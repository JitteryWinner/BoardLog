package com.example.boardlog.funcionalidades.perfiljugador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.boardlog.diseno.Encabezado
import com.example.boardlog.diseno.ListaAplicacion
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.PortadaJuego
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.diseno.VerdeBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel

@Composable
fun PantallaPerfilJugador(
    repositorio: RepositorioBoardLog,
    idJugador: String,
    irA: (Destino) -> Unit,
    alVolver: () -> Unit
) {
    val modeloVista: PerfilJugadorViewModel =
        viewModel(factory = FabricaViewModel { PerfilJugadorViewModel(repositorio, idJugador) });
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle(); LaunchedEffect(
        modeloVista
    ) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(
        title = "Perfil de jugador",
        back = true,
        alVolver = alVolver,
        alNavegar = { modeloVista.alEvento(EventoPerfilJugador.Abrir(it)) }) {
        val p = estadoPantalla.jugador
        if (p == null) {
            Text("Jugador no encontrado"); return@PaginaBoardLog
        }
        Encabezado(p.nombre)
        val finalizadas = estadoPantalla.completadas;
        val victorias = estadoPantalla.victorias
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileMetric(
                "${estadoPantalla.partidas.size}",
                "PARTIDAS"
            ); ProfileMetric("$victorias", "VICTORIAS"); ProfileMetric(
            "${estadoPantalla.porcentaje}%",
            "% VICTORIAS"
        )
        }
        Text("Derrotas: ${finalizadas.count { it.idGanador() != null && it.idGanador() != p.id }} · Empates: ${finalizadas.count { it.idGanador() == null }}")
        Spacer(Modifier.height(20.dp)); Text(
        "Rendimiento",
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp
    )
        Text("$victorias victorias en total · Últimas ${finalizadas.size.coerceAtMost(5)} partidas")
        Row {
            finalizadas.take(5).forEach { partida ->
                Text(
                    if (partida.idGanador() == p.id) "🟢" else "🔴",
                    fontSize = 26.sp
                )
            }
        }
        Spacer(Modifier.height(12.dp)); Text("Últimos resultados", fontWeight = FontWeight.Bold)
        finalizadas.take(5).forEach { partida ->
            TextButton(onClick = {
                modeloVista.alEvento(
                    EventoPerfilJugador.Abrir(
                        Destino.DetallePartida(
                            partida.id
                        )
                    )
                )
            }) {
                Text("${CatalogoJuegos.obtener(partida.idJuego)?.nombre ?: "Juego"}: ${if (partida.idGanador() == null) "Empate" else if (partida.idGanador() == p.id) "Victoria" else "Derrota"}")
            }
        }
        Spacer(Modifier.height(16.dp)); Text(
        "Juegos más jugados",
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp
    )
        ListaAplicacion {
            val juegos = estadoPantalla.juegosMasJugados; items(juegos) { item ->
            val g = item.juego; TarjetaBoardLog(onClick = {
            modeloVista.alEvento(
                EventoPerfilJugador.Abrir(
                    Destino.InformacionJuego(g.id)
                )
            )
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PortadaJuego(
                    g,
                    Modifier.size(46.dp)
                ); Spacer(Modifier.width(12.dp)); Text("${g.nombre} · ${item.cantidad} partidas")
            }
        }
        }
        }
    }
}

@Composable
private fun ProfileMetric(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeBoardLog
        ); Text(label, fontSize = 11.sp)
    }
}
