package com.example.boardlog.datos

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID

/** Única fuente de datos para todos los ViewModels: Room + selección temporal. */
class RepositorioBoardLog(context: Context) {
    private val accesoDatos = Room.databaseBuilder(context.applicationContext, BaseDatosBoardLog::class.java, "boardlog.db")
        .build().dao()
    private val alcance = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val bloqueo = Mutex()
    private val _jugadores = MutableStateFlow<List<EntidadJugador>>(emptyList())
    val jugadores: StateFlow<List<EntidadJugador>> = _jugadores
    private val _partidas = MutableStateFlow<List<EntidadPartida>>(emptyList())
    val partidas: StateFlow<List<EntidadPartida>> = _partidas
    private val _idsJugadoresPendientes = MutableStateFlow<Set<String>>(emptySet())
    val idsJugadoresPendientes: StateFlow<Set<String>> = _idsJugadoresPendientes

    init {
        alcance.launch { accesoDatos.observarJugadores().collectLatest { _jugadores.value = it } }
        alcance.launch { accesoDatos.observarPartidas().collectLatest { _partidas.value = it } }
        alcance.launch { crearDatosEjemplo() }
    }

    private suspend fun crearDatosEjemplo() = bloqueo.withLock {
        if (accesoDatos.contarJugadores() == 0) {
            listOf("David", "Jorge", "Wilfred", "Ana", "Sara").forEachIndexed { index, nombre ->
                accesoDatos.guardarJugador(EntidadJugador("p${index + 1}", nombre))
            }
        }
        if (accesoDatos.contarPartidas() == 0) {
            val ahora = System.currentTimeMillis()
            accesoDatos.guardarPartida(EntidadPartida("demo-risk", "risk", "p1,p2,p3", "p1:5,p2:7,p3:3", ESTADO_EN_CURSO, ahora - 1_200_000))
            accesoDatos.guardarPartida(EntidadPartida("demo-catan", "catan", "p1,p2,p3", "p1:10,p2:8,p3:6", ESTADO_FINALIZADO, ahora - 172_800_000, ahora - 172_000_000))
            accesoDatos.guardarPartida(EntidadPartida("demo-uno", "uno", "p1,p2", "p1:3,p2:6", ESTADO_FINALIZADO, ahora - 345_600_000, ahora - 345_000_000))
        }
    }

    fun obtenerPartida(id: String) = partidas.value.find { it.id == id }
    fun obtenerJugador(id: String) = jugadores.value.find { it.id == id }
    fun jugadoresSeleccionados() = jugadores.value.filter { it.id in idsJugadoresPendientes.value }
    fun alternarJugadorPendiente(id: String) {
        _idsJugadoresPendientes.value = _idsJugadoresPendientes.value.let { if (id in it) it - id else it + id }
    }
    fun reiniciarSeleccion() { _idsJugadoresPendientes.value = emptySet() }

    suspend fun crearPartida(idJuego: String): String? {
        val juego = CatalogoJuegos.obtener(idJuego) ?: return null
        val ids = _idsJugadoresPendientes.value.toList()
        if (ids.size !in juego.minimoJugadores..juego.maximoJugadores) return null
        val id = UUID.randomUUID().toString()
        accesoDatos.guardarPartida(EntidadPartida(id, idJuego, ids.joinToString(","), ids.joinToString(",") { "$it:0" }, ESTADO_EN_CURSO, System.currentTimeMillis()))
        reiniciarSeleccion()
        return id
    }

    suspend fun cambiarPuntos(idPartida: String, idJugador: String, delta: Int) = bloqueo.withLock {
        val partida = accesoDatos.buscarPartida(idPartida) ?: return@withLock
        if (partida.estadoPartida != ESTADO_EN_CURSO || idJugador !in partida.ids()) return@withLock
        val puntos = partida.puntos().toMutableMap()
        puntos[idJugador] = ((puntos[idJugador] ?: 0) + delta).coerceAtLeast(0)
        accesoDatos.guardarPartida(partida.copy(puntuaciones = partida.ids().joinToString(",") { "$it:${puntos[it] ?: 0}" }))
    }

    suspend fun finalizarPartida(idPartida: String) = bloqueo.withLock {
        val partida = accesoDatos.buscarPartida(idPartida) ?: return@withLock
        if (partida.estadoPartida == ESTADO_EN_CURSO) accesoDatos.guardarPartida(partida.copy(estadoPartida = ESTADO_FINALIZADO, fechaFinalizacion = System.currentTimeMillis()))
    }
}
