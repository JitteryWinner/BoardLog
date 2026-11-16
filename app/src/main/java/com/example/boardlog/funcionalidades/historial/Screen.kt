package com.example.boardlog.funcionalidades.historial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.BarraBusqueda
import com.example.boardlog.diseno.Encabezado
import com.example.boardlog.diseno.ListaAplicacion
import com.example.boardlog.diseno.NaranjaBoardLog
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.PortadaJuego
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.diseno.VerdeBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel

@Composable
fun PantallaHistorial(repositorio: RepositorioBoardLog, irA: (Destino) -> Unit) {
    val modeloVista: HistorialViewModel =
        viewModel(factory = FabricaViewModel { HistorialViewModel(repositorio) });
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle(); LaunchedEffect(
        modeloVista
    ) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(alNavegar = { modeloVista.alEvento(EventoHistorial.Abrir(it)) }) {
        Encabezado("Historial")
        BarraBusqueda(
            estadoPantalla.busqueda,
            "Buscar juego o jugador",
            { modeloVista.alEvento(EventoHistorial.Buscar(it)) })
        ListaAplicacion {
            val filtradas = estadoPantalla.partidasVisibles
            if (filtradas.isEmpty()) item { Text("No se encontraron partidas finalizadas.") }
            items(filtradas, key = { it.id }) { m ->
                val g = CatalogoJuegos.obtener(m.idJuego)
                TarjetaBoardLog(onClick = {
                    modeloVista.alEvento(
                        EventoHistorial.Abrir(
                            Destino.DetallePartida(
                                m.id
                            )
                        )
                    )
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (g != null) PortadaJuego(
                            g,
                            Modifier.size(84.dp)
                        ); Spacer(Modifier.width(12.dp)); Column {
                        Text(
                            g?.nombre ?: "Juego",
                            fontWeight = FontWeight.Bold
                        ); Text("${m.ids().size} jugadores"); Text(
                        if (m.idGanador() == null) "Empate / sin ganador" else "🏆 ${estadoPantalla.jugadores.find { it.id == m.idGanador() }?.nombre ?: "Jugador"} ganó",
                        fontSize = 12.sp,
                        color = VerdeBoardLog
                    ); Text("Ver detalle", color = NaranjaBoardLog, fontSize = 12.sp)
                    }
                    }
                }
            }
        }
    }
}
