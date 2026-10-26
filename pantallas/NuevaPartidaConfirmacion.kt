package com.example.boardlog.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NuevaPartidaConfirmacionRoute() {
    NuevaPartidaConfirmacionScreen(
        juego = BLDatos.juegos[1],
        jugadores = BLDatos.jugadores
    )
}

@Composable
fun NuevaPartidaConfirmacionScreen(
    juego: BLJuego,
    jugadores: List<BLJugador>
) {
    BLMarco(
        titulo = "Nueva partida",
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
                        mostrarEditar = true
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    BLTitulo("Jugadores")
                }

                items(
                    items = jugadores,
                    key = { it.carnet }
                ) { jugador ->
                    BLFilaJugador(
                        jugador = jugador,
                        seleccionado = true,
                        editable = false
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {}) {
                            Text("Editar jugadores")
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BLBoton(texto = "Iniciar partida")

                BLBoton(
                    texto = "Cancelar",
                    rojo = true
                )
            }
        }
    }
}

@Preview(
    name = "Nueva partida - confirmación",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun NuevaPartidaConfirmacionPreview() {
    NuevaPartidaConfirmacionRoute()
}