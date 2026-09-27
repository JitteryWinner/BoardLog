package com.example.boardlog.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PartidaEnProgresoRoute() {
    PartidaEnProgresoScreen(
        juego = BLDatos.juegos[1],
        jugadores = BLDatos.jugadores,
        tiempo = "40 min"
    )
}

@Composable
fun PartidaEnProgresoScreen(
    juego: BLJuego,
    jugadores: List<BLJugador>,
    tiempo: String,
    onCambiarPuntos: (BLJugador, Int) -> Unit = { _, _ -> }
) {
    BLMarco(
        titulo = "Partida en progreso",
        mostrarBarra = false
    ) { espacio ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(horizontal = 26.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    BLResumenJuego(
                        juego = juego,
                        tiempo = tiempo
                    )
                }

                item {
                    BLTitulo("Puntuaciones")
                }

                items(
                    items = jugadores,
                    key = { it.carnet }
                ) { jugador ->
                    BLTarjetaPuntuacion(
                        jugador = jugador,
                        onRestar = { onCambiarPuntos(jugador, -1) },
                        onSumar = { onCambiarPuntos(jugador, 1) }
                    )
                }
            }

            Column(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BLBoton(texto = "Guardar y salir")

                BLBoton(
                    texto = "Finalizar partida",
                    rojo = true
                )
            }
        }
    }
}

@Composable
private fun BLTarjetaPuntuacion(
    jugador: BLJugador,
    onRestar: () -> Unit,
    onSumar: () -> Unit
) {
    BLTarjeta {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BLAvatar(jugador.nombre)

                Text(
                    text = jugador.nombre,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onRestar) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Restar punto a ${jugador.nombre}"
                    )
                }

                Text(
                    text = jugador.puntos.toString(),
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(onClick = onSumar) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Sumar punto a ${jugador.nombre}"
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Partida en progreso",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PartidaEnProgresoPreview() {
    PartidaEnProgresoRoute()
}