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
fun NuevaPartidaJugadoresRoute() {
    NuevaPartidaJugadoresScreen(
        jugadores = BLDatos.jugadores,
        seleccionados = BLDatos.jugadores.map { it.carnet }.toSet(),
        puedeContinuar = true
    )
}

@Composable
fun NuevaPartidaJugadoresScreen(
    jugadores: List<BLJugador>,
    seleccionados: Set<String>,
    puedeContinuar: Boolean,
    busqueda: String = "",
    requisito: String = "Catan requiere de 3 a 4 jugadores.",
    mensajeError: String? = null,
    onBuscar: (String) -> Unit = {},
    onSeleccionar: (BLJugador) -> Unit = {}
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
                    indicacion = "Buscar jugador",
                    onCambio = onBuscar
                )
            }

            item {
                BLTitulo("Jugadores")
            }

            if (jugadores.isEmpty()) {
                item {
                    BLVacio("No hay jugadores para mostrar.")
                }
            }

            items(
                items = jugadores,
                key = { it.carnet }
            ) { jugador ->
                BLFilaJugador(
                    jugador = jugador,
                    seleccionado = jugador.carnet in seleccionados,
                    onSeleccionar = { onSeleccionar(jugador) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${seleccionados.size} jugadores seleccionados",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Text(
                    text = requisito,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (mensajeError != null) {
                item {
                    Text(
                        text = mensajeError,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            item {
                BLBoton(texto = "Nuevo jugador")
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                BLBoton(
                    texto = "Siguiente",
                    habilitado = puedeContinuar
                )
            }
        }
    }
}

@Preview(
    name = "Nueva partida - jugadores",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun NuevaPartidaJugadoresPreview() {
    NuevaPartidaJugadoresRoute()
}