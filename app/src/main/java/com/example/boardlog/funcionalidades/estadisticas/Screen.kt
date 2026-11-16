package com.example.boardlog.funcionalidades.estadisticas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.Encabezado
import com.example.boardlog.diseno.ListaAplicacion
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.PortadaJuego
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.diseno.VerdeBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel

@Composable
fun PantallaEstadisticas(repositorio: RepositorioBoardLog, irA: (Destino) -> Unit) {
    val modeloVista: EstadisticasViewModel =
        viewModel(factory = FabricaViewModel { EstadisticasViewModel(repositorio) });
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle(); LaunchedEffect(
        modeloVista
    ) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(alNavegar = { modeloVista.alEvento(EventoEstadisticas.Abrir(it)) }) {
        Encabezado("Estadísticas por jugador")
        var menuJugadoresAbierto by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { menuJugadoresAbierto = true }) {
                Text(
                    estadoPantalla.jugadores.find { it.id == estadoPantalla.idJugadorSeleccionado }?.nombre
                        ?: "Seleccionar jugador"
                )
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Elegir jugador")
            }
            DropdownMenu(
                expanded = menuJugadoresAbierto,
                onDismissRequest = { menuJugadoresAbierto = false }) {
                estadoPantalla.jugadores.forEach { jugador ->
                    DropdownMenuItem(text = { Text(jugador.nombre) }, onClick = {
                        modeloVista.alEvento(EventoEstadisticas.SeleccionarJugador(jugador.id))
                        menuJugadoresAbierto = false
                    })
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            FiltroFecha.entries.forEach { f ->
                FilterChip(
                    selected = estadoPantalla.filtro == f,
                    onClick = { modeloVista.alEvento(EventoEstadisticas.Filtrar(f)) },
                    label = {
                        Text(
                            when (f) {
                                FiltroFecha.MES -> "Este mes"; FiltroFecha.ANIO -> "Este año"; FiltroFecha.TODOS -> "Todo"
                            }
                        )
                    })
            }
        }
        Spacer(Modifier.height(18.dp)); Text(
        "Resumen del jugador seleccionado",
        fontWeight = FontWeight.Bold
    )
        Spacer(Modifier.height(10.dp)); TarjetaBoardLog {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MetricaEstadistica(
                "${estadoPantalla.jugadas}",
                "Partidas finalizadas"
            ); MetricaEstadistica(
            "${estadoPantalla.porcentajeVictorias}%",
            "Porcentaje de victorias"
        )
        }
    }
        Spacer(Modifier.height(12.dp)); TarjetaBoardLog {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            MetricaEstadistica("${estadoPantalla.victorias}", "Victorias")
            MetricaEstadistica("${estadoPantalla.derrotas}", "Derrotas")
            MetricaEstadistica("${estadoPantalla.empates}", "Empates")
        }
    }
        Spacer(Modifier.height(12.dp)); Text("Últimas partidas", fontWeight = FontWeight.Bold)
        estadoPantalla.partidasFiltradas.take(3).forEach { partida ->
            TextButton(onClick = {
                modeloVista.alEvento(
                    EventoEstadisticas.Abrir(
                        Destino.DetallePartida(
                            partida.id
                        )
                    )
                )
            }) {
                Text("${CatalogoJuegos.obtener(partida.idJuego)?.nombre ?: "Juego"} · ${if (partida.idGanador() == null) "Empate" else if (partida.idGanador() == estadoPantalla.idJugadorSeleccionado) "Victoria" else "Derrota"}")
            }
        }
        Spacer(Modifier.height(18.dp)); Text("Mis mejores juegos", fontWeight = FontWeight.Bold)
        ListaAplicacion {
            if (estadoPantalla.juegosMasJugados.isEmpty()) item { Text("Todavía no hay partidas en este período.") }
            items(estadoPantalla.juegosMasJugados) { entry ->
                TarjetaBoardLog {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PortadaJuego(
                            entry.juego,
                            Modifier.size(56.dp)
                        ); Spacer(Modifier.width(10.dp)); Column {
                        Text(
                            entry.juego.nombre,
                            fontWeight = FontWeight.Bold
                        ); Text("${entry.partidas} partidas · ${entry.victorias} victorias")
                    }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricaEstadistica(value: String, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeBoardLog
        ); Text(title, fontSize = 12.sp)
    }
}
