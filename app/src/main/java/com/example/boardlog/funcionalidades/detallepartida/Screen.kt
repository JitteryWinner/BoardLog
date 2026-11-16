package com.example.boardlog.funcionalidades.detallepartida

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.Encabezado
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.PortadaJuego
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.diseno.VerdeBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel

@Composable
fun PantallaDetallePartida(
    repositorio: RepositorioBoardLog,
    idPartida: String,
    irA: (Destino) -> Unit,
    alVolver: () -> Unit
) {
    val rondas by repositorio.rondas.collectAsStateWithLifecycle()
    val modeloVista: DetallePartidaViewModel = viewModel(
        factory = FabricaViewModel { DetallePartidaViewModel(repositorio, idPartida) }
    )
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()

    LaunchedEffect(modeloVista) {
        modeloVista.navegacion.collect { irA(it) }
    }

    PaginaBoardLog(
        title = "Detalle de partida",
        back = true,
        alVolver = alVolver,
        alNavegar = { modeloVista.alEvento(EventoDetallePartida.Abrir(it)) }
    ) {
        Column(Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            val m = estadoPantalla.partida
            if (m == null) {
                Text("Cargando partida...")
                return@Column
            }

            val g = CatalogoJuegos.obtener(m.idJuego)
            if (g != null) {
                PortadaJuego(g, Modifier
                    .fillMaxWidth()
                    .height(190.dp))
            }

            Encabezado("${g?.nombre ?: "Juego"} - Partida")
            Text("Posiciones", fontWeight = FontWeight.Bold)

            val clasificados = estadoPantalla.clasificacion
            clasificados.forEachIndexed { idx, entry ->
                TarjetaBoardLog {
                    Row {
                        Text("${idx + 1}.", Modifier.width(35.dp), fontWeight = FontWeight.Bold)
                        Text(entry.nombre, Modifier.weight(1f))
                        Text("${entry.puntos} pts")
                    }
                }
                Spacer(Modifier.height(7.dp))
            }

            val historialRondas = rondas.filter { it.idPartida == m.id }
                .groupBy { it.numeroRonda }

            if (historialRondas.isNotEmpty()) {
                Spacer(Modifier.height(14.dp))
                Text("Puntuaciones por ronda", fontWeight = FontWeight.Bold)

                historialRondas.toSortedMap().forEach { (numero, puntuaciones) ->
                    Text("Ronda $numero", fontWeight = FontWeight.SemiBold)
                    puntuaciones.forEach { r ->
                        Text(
                            "${estadoPantalla.jugadores.find { it.id == r.idJugador }?.nombre ?: "Jugador"}: +${r.puntos}"
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            Text(
                "Ganador: ${estadoPantalla.nombreGanador}",
                fontWeight = FontWeight.Bold,
                color = VerdeBoardLog
            )
        }
    }
}
