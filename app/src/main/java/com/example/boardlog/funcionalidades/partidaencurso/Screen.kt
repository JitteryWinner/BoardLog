package com.example.boardlog.funcionalidades.partidaencurso

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.ESTADO_EN_CURSO
import com.example.boardlog.datos.ESTADO_FINALIZADO
import com.example.boardlog.datos.ReglasPuntuacion
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.BotonPrincipal
import com.example.boardlog.diseno.ListaAplicacion
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.PortadaJuego
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaPartidaEnCurso(
    repositorio: RepositorioBoardLog,
    idPartida: String,
    irA: (Destino) -> Unit,
    alVolver: () -> Unit
) {
    val alcance = rememberCoroutineScope()
    var mostrarRonda by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var puntajesRonda by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var confirmarFinalizar by remember { mutableStateOf(false) }
    val rondas by repositorio.rondas.collectAsStateWithLifecycle()
    val modeloVista: PartidaEnCursoViewModel =
        viewModel(factory = FabricaViewModel { PartidaEnCursoViewModel(repositorio, idPartida) });
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle(); LaunchedEffect(
        modeloVista
    ) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(
        title = "Partida en progreso",
        back = true,
        barraInferior = false,
        alVolver = alVolver
    ) {
        val m = estadoPantalla.partida
        if (m == null) {
            Text("Cargando partida..."); return@PaginaBoardLog
        }
        val g = estadoPantalla.juego
        Spacer(Modifier.height(12.dp)); if (g != null) TarjetaBoardLog {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PortadaJuego(
                g,
                Modifier.size(100.dp)
            ); Spacer(Modifier.width(12.dp)); Column {
            Text(
                g.nombre,
                fontWeight = FontWeight.Bold
            ); Text("${estadoPantalla.jugadores.size} jugadores")
        }
        }
    }
        val regla = ReglasPuntuacion.obtener(m.idJuego)
        if (mostrarRonda) AlertDialog(
            onDismissRequest = { mostrarRonda = false },
            title = { Text("Registrar ronda") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Ingresa los puntos obtenidos en esta ronda por cada jugador.")
                    estadoPantalla.jugadores.forEach { jugador ->
                        OutlinedTextField(
                            value = puntajesRonda[jugador.id] ?: "0",
                            onValueChange = { valor ->
                                if (valor.length <= 7 && valor.all { it.isDigit() }) puntajesRonda =
                                    puntajesRonda + (jugador.id to valor)
                            },
                            label = { Text(jugador.nombre) },
                            singleLine = true
                        )
                    }
                    mensajeError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    alcance.launch {
                        val puntos = estadoPantalla.jugadores.associate {
                            it.id to (puntajesRonda[it.id]?.toIntOrNull() ?: -1)
                        }
                        mensajeError = repositorio.guardarRonda(m.id, puntos)
                        if (mensajeError == null) mostrarRonda = false
                    }
                }) { Text("Guardar ronda") }
            },
            dismissButton = { TextButton(onClick = { mostrarRonda = false }) { Text("Cancelar") } })
        if (confirmarFinalizar) AlertDialog(
            onDismissRequest = { confirmarFinalizar = false },
            title = { Text("Finalizar partida") },
            text = { Text("¿Confirmas el cierre de la partida? Después no podrás modificar los puntos.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmarFinalizar = false; modeloVista.alEvento(EventoPartidaEnCurso.Finalizar)
                }) { Text("Finalizar") }
            },
            dismissButton = {
                TextButton(onClick = {
                    confirmarFinalizar = false
                }) { Text("Cancelar") }
            })
        Spacer(Modifier.height(20.dp)); Text(
        "Puntuaciones · ${regla.unidad}",
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold
    )
        Text(regla.descripcion, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(10.dp))
        if (m.idJuego != "ajedrez" && m.estadoPartida == ESTADO_EN_CURSO) {
            Button(onClick = {
                puntajesRonda = estadoPantalla.jugadores.associate { it.id to "0" }; mensajeError =
                null; mostrarRonda = true
            }, modifier = Modifier.fillMaxWidth()) { Text("Registrar nueva ronda") }
        }
        val historialRondas = rondas.filter { it.idPartida == m.id }.groupBy { it.numeroRonda }
        if (historialRondas.isNotEmpty()) {
            Text("Rondas registradas: ${historialRondas.size}", fontWeight = FontWeight.SemiBold)
            historialRondas.toSortedMap().forEach { (numero, entradas) ->
                Text(
                    "Ronda $numero: " + entradas.joinToString(" · ") { e -> "${estadoPantalla.jugadores.find { it.id == e.idJugador }?.nombre ?: "Jugador"} +${e.puntos}" },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        ListaAplicacion(Modifier.weight(1f)) {
            items(estadoPantalla.jugadores, key = { it.id }) { p ->
                TarjetaBoardLog {
                    Text(
                        p.nombre,
                        fontWeight = FontWeight.Bold
                    ); Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        modeloVista.alEvento(
                            EventoPartidaEnCurso.CambiarPuntos(
                                p.id,
                                -regla.paso
                            )
                        )
                    }, enabled = m.estadoPartida == ESTADO_EN_CURSO && m.idJuego != "ajedrez") {
                        Icon(
                            Icons.Default.Remove,
                            "Restar"
                        )
                    }; Text(
                    "${estadoPantalla.puntuaciones[p.id] ?: 0}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ); IconButton(onClick = {
                    modeloVista.alEvento(
                        EventoPartidaEnCurso.CambiarPuntos(
                            p.id,
                            regla.paso
                        )
                    )
                }, enabled = m.estadoPartida == ESTADO_EN_CURSO && m.idJuego != "ajedrez") {
                    Icon(
                        Icons.Default.Add,
                        "Sumar"
                    )
                }
                }
                }
            }
        }
        if (m.estadoPartida == ESTADO_FINALIZADO) BotonPrincipal("Ver resultados") {
            modeloVista.alEvento(
                EventoPartidaEnCurso.Resultados
            )
        } else {
            if (m.idJuego == "ajedrez") {
                Text("Selecciona el resultado del ajedrez", fontWeight = FontWeight.Bold)
                estadoPantalla.jugadores.forEach { jugador ->
                    OutlinedButton(onClick = {
                        alcance.launch {
                            mensajeError = repositorio.guardarResultadoAjedrez(
                                m.id,
                                jugador.id
                            ); if (mensajeError == null) modeloVista.alEvento(EventoPartidaEnCurso.Resultados)
                        }
                    }, modifier = Modifier.fillMaxWidth()) { Text("Ganó ${jugador.nombre}") }
                }
                OutlinedButton(onClick = {
                    alcance.launch {
                        mensajeError = repositorio.guardarResultadoAjedrez(
                            m.id,
                            null
                        ); if (mensajeError == null) modeloVista.alEvento(EventoPartidaEnCurso.Resultados)
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Tablas (empate)") }
                mensajeError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
            BotonPrincipal("Guardar y salir") { modeloVista.alEvento(EventoPartidaEnCurso.Guardar) }
            Spacer(Modifier.height(8.dp)); if (m.idJuego != "ajedrez") BotonPrincipal(
                "Finalizar partida",
                esPeligroso = true,
                enabled = !estadoPantalla.ocupado
            ) { confirmarFinalizar = true }
        }
    }
}
