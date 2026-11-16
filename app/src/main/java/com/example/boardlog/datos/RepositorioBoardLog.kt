package com.example.boardlog.datos

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.UUID

private val Context.sesionDataStore by preferencesDataStore(name = "boardlog_sesion")
private val CLAVE_SESION = stringPreferencesKey("cuenta_activa")

/** Única fuente de datos para todos los ViewModels: Room + selección temporal. */
class RepositorioBoardLog(context: Context) {
    private val contexto = context.applicationContext
    private val datosSesion = contexto.sesionDataStore
    private val _sesionLista = MutableStateFlow(false)
    val sesionLista: StateFlow<Boolean> = _sesionLista
    private val _cuentaActiva = MutableStateFlow<EntidadCuenta?>(null)
    val cuentaActiva: StateFlow<EntidadCuenta?> = _cuentaActiva

    private val accesoDatos = Room.databaseBuilder(
        context.applicationContext,
        BaseDatosBoardLog::class.java,
        "boardlog.db"
    )
        .addMigrations(MIGRACION_1_2, MIGRACION_2_3).build().dao()
    private val alcance = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val bloqueo = Mutex()
    private val _jugadores = MutableStateFlow<List<EntidadJugador>>(emptyList())
    val jugadores: StateFlow<List<EntidadJugador>> = _jugadores
    private val _rondas = MutableStateFlow<List<EntidadRonda>>(emptyList())
    val rondas: StateFlow<List<EntidadRonda>> = _rondas
    private val _partidas = MutableStateFlow<List<EntidadPartida>>(emptyList())
    val partidas: StateFlow<List<EntidadPartida>> = _partidas
    private val _idsJugadoresPendientes = MutableStateFlow<Set<String>>(emptySet())
    val idsJugadoresPendientes: StateFlow<Set<String>> = _idsJugadoresPendientes

    init {
        alcance.launch {
            val sesionGuardada = datosSesion.data.first()[CLAVE_SESION]
            _cuentaActiva.value = sesionGuardada?.let { accesoDatos.buscarCuentaId(it) }
            _sesionLista.value = true
        }
        alcance.launch {
            combine(
                accesoDatos.observarJugadores(),
                cuentaActiva
            ) { lista, cuenta -> lista.filter { it.idPropietario == cuenta?.id } }.collectLatest {
                _jugadores.value = it
            }
        }
        alcance.launch {
            combine(accesoDatos.observarRondas(), partidas) { rondas, partidas ->
                val ids = partidas.map { it.id }.toSet()
                rondas.filter { it.idPartida in ids }
            }.collectLatest { _rondas.value = it }
        }
        alcance.launch {
            combine(
                accesoDatos.observarPartidas(),
                cuentaActiva
            ) { lista, cuenta -> lista.filter { it.idPropietario == cuenta?.id } }.collectLatest {
                _partidas.value = it
            }
        }
    }

    private fun hashPin(pin: String, sal: String): String = MessageDigest.getInstance("SHA-256")
        .digest("$sal:$pin".toByteArray()).joinToString("") { "%02x".format(it) }

    /** Perfiles locales para uso académico; no representan autenticación remota. */
    suspend fun entrar(usuario: String, pin: String, crear: Boolean): String? = bloqueo.withLock {
        val nombre = usuario.trim()
        if (nombre.length !in 3..25 || pin.length !in 4..12) return@withLock "Usuario de 3 a 25 caracteres y PIN de 4 a 12 dígitos."
        if (!pin.all { it.isDigit() }) return@withLock "El PIN debe contener solo números."
        val encontrada = accesoDatos.buscarCuenta(nombre.lowercase())
        val cuenta = if (crear) {
            if (encontrada != null) return@withLock "Ese usuario ya existe."
            val sal = ByteArray(16).also { SecureRandom().nextBytes(it) }
                .joinToString("") { "%02x".format(it) }
            EntidadCuenta(
                UUID.randomUUID().toString(),
                nombre.lowercase(),
                hashPin(pin, sal),
                sal
            ).also { accesoDatos.guardarCuenta(it) }
        } else {
            if (encontrada == null || encontrada.hashPin != hashPin(
                    pin,
                    encontrada.sal
                )
            ) return@withLock "Usuario o PIN incorrecto."
            encontrada
        }
        _jugadores.value = emptyList()
        _partidas.value = emptyList()
        _rondas.value = emptyList()
        _cuentaActiva.value = cuenta
        _idsJugadoresPendientes.value = emptySet()
        datosSesion.edit { it[CLAVE_SESION] = cuenta.id }
        null
    }

    suspend fun salir() {
        datosSesion.edit { it.remove(CLAVE_SESION) }
        _cuentaActiva.value = null
        _jugadores.value = emptyList()
        _partidas.value = emptyList()
        _rondas.value = emptyList()
        _idsJugadoresPendientes.value = emptySet()
    }

    suspend fun agregarJugador(nombre: String): String? = bloqueo.withLock {
        val cuenta = _cuentaActiva.value ?: return@withLock "Inicia sesión para agregar jugadores."
        val limpio = nombre.trim()
        if (limpio.length !in 2..30) return@withLock "Ingresa un nombre de 2 a 30 caracteres."
        if (_jugadores.value.any {
                !it.archivado && it.nombre.equals(
                    limpio,
                    ignoreCase = true
                )
            }) return@withLock "Ya existe un jugador con ese nombre."
        accesoDatos.guardarJugador(EntidadJugador(UUID.randomUUID().toString(), limpio, cuenta.id))
        null
    }

    /** No borramos físicamente: así se conservan los nombres en partidas antiguas. */
    suspend fun editarJugador(id: String, nombre: String): String? = bloqueo.withLock {
        val cuenta = _cuentaActiva.value ?: return@withLock "Inicia sesión."
        val actual = _jugadores.value.find { it.id == id && !it.archivado }
            ?: return@withLock "Jugador no encontrado."
        val limpio = nombre.trim()
        if (limpio.length !in 2..30) return@withLock "Nombre de 2 a 30 caracteres."
        if (_jugadores.value.any {
                !it.archivado && it.id != actual.id && it.nombre.equals(
                    limpio,
                    true
                )
            }) return@withLock "El nombre ya está en uso."
        accesoDatos.renombrarJugador(id, cuenta.id, limpio)
        null
    }

    suspend fun eliminarJugador(id: String): String? = bloqueo.withLock {
        val cuenta = _cuentaActiva.value ?: return@withLock "Inicia sesión."
        if (_jugadores.value.none { it.id == id && !it.archivado }) return@withLock "Jugador no encontrado."
        if (_partidas.value.any { it.estadoPartida == ESTADO_EN_CURSO && id in it.ids() })
            return@withLock "No puedes eliminar a un jugador con una partida en curso. Finaliza esa partida primero."
        accesoDatos.archivarJugador(id, cuenta.id)
        _idsJugadoresPendientes.value = _idsJugadoresPendientes.value - id
        null
    }

    /** Suma los puntos de una ronda completa; solo se permite en partidas activas. */
    suspend fun guardarRonda(idPartida: String, puntosPorJugador: Map<String, Int>): String? =
        bloqueo.withLock {
            val partida =
                accesoDatos.buscarPartida(idPartida) ?: return@withLock "Partida no encontrada."
            if (partida.idPropietario != _cuentaActiva.value?.id || partida.estadoPartida != ESTADO_EN_CURSO)
                return@withLock "La partida no está disponible."
            if (partida.idJuego == "ajedrez") return@withLock "Ajedrez se registra mediante resultado, no rondas."
            if (puntosPorJugador.keys != partida.ids()
                    .toSet() || puntosPorJugador.values.any { it < 0 || it > 100000 }
            )
                return@withLock "Ingresa un puntaje válido para cada jugador."
            val siguiente = (accesoDatos.ultimaRonda(idPartida) ?: 0) + 1
            val ahora = System.currentTimeMillis()
            val puntos = partida.puntos().toMutableMap()
            puntosPorJugador.forEach { (id, valor) ->
                puntos[id] =
                    ((puntos[id] ?: 0).toLong() + valor).coerceAtMost(Int.MAX_VALUE.toLong())
                        .toInt()
            }
            accesoDatos.guardarRondas(puntosPorJugador.map { (id, valor) ->
                EntidadRonda(
                    UUID.randomUUID().toString(), idPartida, id, siguiente, valor, ahora
                )
            })
            accesoDatos.guardarPartida(
                partida.copy(
                    puntuaciones = partida.ids().joinToString(",") { "$it:${puntos[it] ?: 0}" })
            )
            null
        }

    suspend fun guardarResultadoAjedrez(idPartida: String, ganador: String?): String? =
        bloqueo.withLock {
            val partida =
                accesoDatos.buscarPartida(idPartida) ?: return@withLock "Partida no encontrada."
            if (partida.idPropietario != _cuentaActiva.value?.id || partida.idJuego != "ajedrez" || partida.estadoPartida != ESTADO_EN_CURSO)
                return@withLock "Partida no disponible."
            if (ganador != null && ganador !in partida.ids()) return@withLock "Jugador no válido."
            val puntos = partida.ids().joinToString(",") { "$it:${if (ganador == it) 1 else 0}" }
            accesoDatos.guardarPartida(
                partida.copy(
                    puntuaciones = puntos,
                    estadoPartida = ESTADO_FINALIZADO,
                    fechaFinalizacion = System.currentTimeMillis()
                )
            )
            null
        }

    fun obtenerPartida(id: String) = partidas.value.find { it.id == id }
    fun obtenerJugador(id: String) = jugadores.value.find { it.id == id }
    fun jugadoresSeleccionados() =
        jugadores.value.filter { !it.archivado && it.id in idsJugadoresPendientes.value }

    fun alternarJugadorPendiente(id: String) {
        _idsJugadoresPendientes.value =
            _idsJugadoresPendientes.value.let { if (id in it) it - id else it + id }
    }

    fun reiniciarSeleccion() {
        _idsJugadoresPendientes.value = emptySet()
    }

    suspend fun crearPartida(idJuego: String): String? {
        val juego = CatalogoJuegos.obtener(idJuego) ?: return null
        val cuenta = _cuentaActiva.value ?: return null
        val ids =
            _idsJugadoresPendientes.value.filter { id -> _jugadores.value.any { it.id == id && !it.archivado } }
        if (ids.size !in juego.minimoJugadores..juego.maximoJugadores) return null
        val id = UUID.randomUUID().toString()
        accesoDatos.guardarPartida(
            EntidadPartida(
                id,
                idJuego,
                ids.joinToString(","),
                ids.joinToString(",") { "$it:0" },
                ESTADO_EN_CURSO,
                System.currentTimeMillis(),
                idPropietario = cuenta.id
            )
        )
        reiniciarSeleccion()
        return id
    }

    suspend fun cambiarPuntos(idPartida: String, idJugador: String, delta: Int) = bloqueo.withLock {
        val partida = accesoDatos.buscarPartida(idPartida) ?: return@withLock
        if (partida.idPropietario != _cuentaActiva.value?.id || partida.estadoPartida != ESTADO_EN_CURSO || idJugador !in partida.ids()) return@withLock
        val regla = ReglasPuntuacion.obtener(partida.idJuego)
        val puntos = partida.puntos().toMutableMap()
        puntos[idJugador] = ((puntos[idJugador] ?: 0) + delta).coerceAtLeast(0)
            .let { regla.maximo?.let { max -> it.coerceAtMost(max) } ?: it }
        accesoDatos.guardarPartida(
            partida.copy(
                puntuaciones = partida.ids().joinToString(",") { "$it:${puntos[it] ?: 0}" })
        )
    }

    suspend fun finalizarPartida(idPartida: String) = bloqueo.withLock {
        val partida = accesoDatos.buscarPartida(idPartida) ?: return@withLock
        if (partida.idPropietario == _cuentaActiva.value?.id && partida.estadoPartida == ESTADO_EN_CURSO) accesoDatos.guardarPartida(
            partida.copy(
                estadoPartida = ESTADO_FINALIZADO,
                fechaFinalizacion = System.currentTimeMillis()
            )
        )
    }
}
