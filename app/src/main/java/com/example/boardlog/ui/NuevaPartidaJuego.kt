package com.example.boardlog.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NuevaPartidaJuegoRoute() {
    NuevaPartidaJuegoScreen(
        juegos = BLDatos.juegos,
        recientes = BLDatos.recientes
    )
}

@Composable
fun NuevaPartidaJuegoScreen(
    juegos: List<BLJuego>,
    recientes: List<BLJuego>,
    busqueda: String = "",
    juegoSeleccionado: String? = null,
    onBuscar: (String) -> Unit = {},
    onSeleccionar: (BLJuego) -> Unit = {}
) {
    BLMarco(titulo = "Nueva partida") { espacio ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio),
            contentPadding = PaddingValues(26.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                BLBuscador(
                    texto = busqueda,
                    indicacion = "Buscar juego",
                    onCambio = onBuscar
                )
            }

            if (recientes.isNotEmpty()) {
                item {
                    BLTitulo("Recientes")
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(recientes) { juego ->
                            Surface(
                                onClick = { onSeleccionar(juego) },
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.surface
                            ) {
                                BLPortada(
                                    juego = juego,
                                    modifier = Modifier
                                        .size(88.dp)
                                        .padding(6.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                BLTitulo("Todos los juegos")
            }

            if (juegos.isEmpty()) {
                item {
                    BLVacio("No encontramos juegos con ese nombre.")
                }
            }

            items(juegos) { juego ->
                BLJuegoSeleccionable(
                    juego = juego,
                    seleccionado = juego.nombre == juegoSeleccionado,
                    onClick = { onSeleccionar(juego) }
                )
            }
        }
    }
}

@Composable
private fun BLJuegoSeleccionable(
    juego: BLJuego,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (seleccionado) 2.dp else 1.dp,
            color = if (seleccionado) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BLPortada(
                juego = juego,
                modifier = Modifier.size(54.dp)
            )

            Text(
                text = juego.nombre,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleSmall
            )

            if (seleccionado) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Juego seleccionado",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(
    name = "Nueva partida - juego",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun NuevaPartidaJuegoPreview() {
    NuevaPartidaJuegoRoute()
}