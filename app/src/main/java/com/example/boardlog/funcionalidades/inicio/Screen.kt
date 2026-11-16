package com.example.boardlog.funcionalidades.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.ESTADO_EN_CURSO
import com.example.boardlog.datos.ESTADO_FINALIZADO
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.BotonPrincipal
import com.example.boardlog.diseno.NaranjaBoardLog
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.PortadaJuego
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.diseno.VerdeBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaInicio(repositorio: RepositorioBoardLog, irA: (Destino) -> Unit, alSalir: () -> Unit) {
    val alcanceSesion = rememberCoroutineScope()
    val cuenta by repositorio.cuentaActiva.collectAsStateWithLifecycle()
    val modeloVista: InicioViewModel =
        viewModel(factory = FabricaViewModel { InicioViewModel(repositorio) })
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()
    LaunchedEffect(modeloVista) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(alNavegar = { modeloVista.alEvento(EventoInicio.Abrir(it)) }) {
        Column(Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "¡Hola, ${cuenta?.usuario ?: "Usuario"}!",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { alcanceSesion.launch { repositorio.salir(); alSalir() } }) {
                    Text(
                        "Salir"
                    )
                }
            }
            Text("¿Qué vamos a jugar hoy?", color = VerdeBoardLog)
            Spacer(Modifier.height(14.dp))
            BotonPrincipal("＋ Nueva Partida") { modeloVista.alEvento(EventoInicio.Abrir(Destino.SeleccionJuego)) }
            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Partida en progreso", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                IconButton(onClick = { modeloVista.alEvento(EventoInicio.Abrir(Destino.Historial)) }) {
                    Icon(
                        Icons.Default.ArrowForward,
                        null
                    )
                }
            }
            val partidaEnCurso =
                estadoPantalla.partidas.firstOrNull { it.estadoPartida == ESTADO_EN_CURSO }
            if (partidaEnCurso == null) Text("No tienes partidas en progreso.") else {
                val g = CatalogoJuegos.obtener(partidaEnCurso.idJuego)
                TarjetaBoardLog(onClick = {
                    modeloVista.alEvento(
                        EventoInicio.Abrir(
                            Destino.PartidaEnCurso(
                                partidaEnCurso.id
                            )
                        )
                    )
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (g != null) PortadaJuego(g, Modifier.size(76.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                g?.nombre ?: "Juego",
                                fontWeight = FontWeight.Bold
                            ); Text("${partidaEnCurso.ids().size} jugadores · En progreso")
                        }
                        Text("Continuar", color = NaranjaBoardLog)
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            Text("Actividad reciente", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            estadoPantalla.partidas.filter { it.estadoPartida == ESTADO_FINALIZADO }.take(3)
                .forEach { partida ->
                    val g = CatalogoJuegos.obtener(partida.idJuego)
                    TarjetaBoardLog(onClick = {
                        modeloVista.alEvento(
                            EventoInicio.Abrir(
                                Destino.DetallePartida(
                                    partida.id
                                )
                            )
                        )
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (g != null) PortadaJuego(g, Modifier.size(55.dp))
                            Column(Modifier
                                .padding(start = 10.dp)
                                .weight(1f)) {
                                Text(
                                    g?.nombre ?: "Juego", fontWeight = FontWeight.Bold
                                ); Text("${partida.ids().size} jugadores")
                            }
                            Text("Ver", color = VerdeBoardLog)
                        }
                    }
                    Spacer(Modifier.height(5.dp))
                }
            Spacer(Modifier.height(18.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                MetricaInicio(
                    "${estadoPantalla.partidas.size}",
                    "Partidas"
                ); MetricaInicio(
                "${CatalogoJuegos.juegos.size}",
                "Juegos"
            ); MetricaInicio("${estadoPantalla.cantidadFinalizadas}", "Finalizadas")
            }
        }
    }
}

@Composable
private fun MetricaInicio(numero: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            numero,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = VerdeBoardLog
        ); Text(label, fontSize = 11.sp)
    }
}
