package com.example.boardlog.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.R

// MODELOS DE EJEMPLO

data class BLJugador(
    val nombre: String,
    val nombreCompleto: String,
    val carnet: String,
    val puntos: Int
)

data class BLJuego(
    val nombre: String,
    @param:DrawableRes val imagen: Int,
    val cantidadJugadores: String
)

data class BLPerfil(
    val jugador: BLJugador,
    val partidas: Int,
    val victorias: Int,
    val resultados: List<Boolean>,
    val juegos: List<BLJuego>
)

// DATOS DE EJEMPLO PARA LAS CINCO PANTALLAS

internal object BLDatos {

    val jugadores = listOf(
        BLJugador(
            nombre = "Jorge",
            nombreCompleto = "Jorge Andres Morales Solorzano",
            carnet = "24284",
            puntos = 6
        ),
        BLJugador(
            nombre = "David",
            nombreCompleto = "David Alejnadro Berganza Monterroso",
            carnet = "25573",
            puntos = 8
        ),
        BLJugador(
            nombre = "Wilfred",
            nombreCompleto = "Wilfred Emilio Orellana Quiroa",
            carnet = "25028",
            puntos = 10
        )
    )

    val juegos = listOf(
        BLJuego("UNO", R.drawable.uno, "2–10 jugadores"),
        BLJuego("Catan", R.drawable.catan, "3–4 jugadores"),
        BLJuego("Ajedrez", R.drawable.ajedrez, "2 jugadores"),
        BLJuego("Clue", R.drawable.clue, "3–6 jugadores"),
        BLJuego("Risk", R.drawable.risk, "2–6 jugadores")
    )

    val recientes = listOf(
        juegos[1],
        juegos[0],
        juegos[2]
    )

    val perfil = BLPerfil(
        jugador = jugadores[1],
        partidas = 24,
        victorias = 10,
        resultados = listOf(true, true, false, true, false),
        juegos = recientes
    )
}

// ESTILO LOCAL PARA ESTAS CINCO PANTALLAS.
// Usa MaterialTheme sin modificar Theme.kt.

@Composable
private fun BLEstilo(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFFF8528),
            onPrimary = Color(0xFF1D1D1D),
            secondary = Color(0xFF07564C),
            onSecondary = Color.White,
            secondaryContainer = Color(0xFFD4EED6),
            onSecondaryContainer = Color(0xFF20532B),
            tertiaryContainer = Color(0xFFFFE4A0),
            onTertiaryContainer = Color(0xFF624600),
            background = Color(0xFFFAF7F2),
            onBackground = Color(0xFF1D1D1D),
            surface = Color.White,
            onSurface = Color(0xFF1D1D1D),
            onSurfaceVariant = Color(0xFF737B83),
            outlineVariant = Color(0xFFD8D8D8),
            error = Color(0xFFBD3030),
            onError = Color.White,
            errorContainer = Color(0xFFFFDAD6),
            onErrorContainer = Color(0xFF8C1D18)
        ),
        typography = Typography().let { base ->
            base.copy(
                titleLarge = base.titleLarge.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                titleMedium = base.titleMedium.copy(
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                titleSmall = base.titleSmall.copy(
                    fontSize = 17.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.Bold
                ),
                headlineSmall = base.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        shapes = Shapes(
            extraSmall = RoundedCornerShape(4.dp),
            small = RoundedCornerShape(10.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(18.dp),
            extraLarge = RoundedCornerShape(50)
        ),
        content = content
    )
}

// COMPONENTES COMPARTIDOS POR LAS CINCO PANTALLAS

@Composable
internal fun BLMarco(
    titulo: String,
    mostrarBarra: Boolean = true,
    contenido: @Composable (PaddingValues) -> Unit
) {
    BLEstilo {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }

                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            bottomBar = {
                if (mostrarBarra) {
                    BLBarraInferior()
                }
            },
            content = contenido
        )
    }
}

@Composable
private fun BLBarraInferior() {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(74.dp)
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Inicio",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Casino,
                    contentDescription = "Juegos",
                    modifier = Modifier.size(30.dp)
                )
            }

            FloatingActionButton(
                onClick = {},
                modifier = Modifier.size(56.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Nueva partida",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.History,
                    contentDescription = "Historial",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.BarChart,
                    contentDescription = "Estadísticas",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

@Composable
internal fun BLTarjeta(
    modifier: Modifier = Modifier,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        content = contenido
    )
}

@Composable
internal fun BLAvatar(nombre: String) {
    Surface(
        modifier = Modifier.size(48.dp),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = nombre.take(1),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
internal fun BLPortada(
    juego: BLJuego,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(juego.imagen),
        contentDescription = juego.nombre,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}

@Composable
internal fun BLTitulo(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
internal fun BLBuscador(
    texto: String,
    indicacion: String,
    onCambio: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = texto,
        onValueChange = onCambio,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = indicacion,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = MaterialTheme.shapes.extraLarge,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )
}

@Composable
internal fun BLBoton(
    texto: String,
    habilitado: Boolean = true,
    rojo: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (rojo) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.primary
            },
            contentColor = if (rojo) {
                MaterialTheme.colorScheme.onError
            } else {
                MaterialTheme.colorScheme.onPrimary
            }
        )
    ) {
        Text(texto)
    }
}

@Composable
internal fun BLFilaJugador(
    jugador: BLJugador,
    seleccionado: Boolean,
    editable: Boolean = true,
    onSeleccionar: () -> Unit = {}
) {
    BLTarjeta {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BLAvatar(jugador.nombre)

            Text(
                text = jugador.nombre,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.weight(1f)
            )

            if (editable) {
                Checkbox(
                    checked = seleccionado,
                    onCheckedChange = { onSeleccionar() },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.secondary
                    )
                )
            } else {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Jugador confirmado",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
internal fun BLResumenJuego(
    juego: BLJuego,
    tiempo: String? = null,
    mostrarEditar: Boolean = false
) {
    BLTarjeta {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BLPortada(
                juego = juego,
                modifier = Modifier
                    .width(110.dp)
                    .height(132.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = juego.nombre,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = juego.cantidadJugadores,
                    style = MaterialTheme.typography.bodyMedium
                )

                if (tiempo != null) {
                    Text(
                        text = tiempo,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (mostrarEditar) {
                    TextButton(onClick = {}) {
                        Text("Editar")
                    }
                }
            }
        }
    }
}

@Composable
internal fun BLVacio(texto: String) {
    BLTarjeta {
        Text(
            text = texto,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ROUTE DEL PERFIL

@Composable
fun PerfilJugadorRoute() {
    PerfilJugadorScreen(
        perfil = BLDatos.perfil
    )
}

// SCREEN DEL PERFIL

@Composable
fun PerfilJugadorScreen(perfil: BLPerfil) {
    BLMarco(titulo = "Perfil de jugador") { espacio ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacio),
            contentPadding = PaddingValues(26.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    BLAvatar(perfil.jugador.nombre)

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = perfil.jugador.nombre,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Text(
                            text = "Jugador desde Ago. 2026",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BLEstadisticaPerfil(
                        titulo = "PARTIDAS",
                        valor = perfil.partidas.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    BLEstadisticaPerfil(
                        titulo = "VICTORIAS",
                        valor = perfil.victorias.toString(),
                        modifier = Modifier.weight(1f)
                    )

                    BLEstadisticaPerfil(
                        titulo = "% VICTORIAS",
                        valor = if (perfil.partidas == 0) {
                            "—"
                        } else {
                            "${perfil.victorias * 100 / perfil.partidas}%"
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                BLTitulo("Rendimiento")
            }

            if (perfil.resultados.isEmpty()) {
                item {
                    BLVacio("Todavía no hay partidas registradas.")
                }
            } else {
                item {
                    Text(
                        text = "${perfil.victorias} victorias en total\n" +
                                "Últimas ${perfil.resultados.takeLast(5).size} partidas",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                item {
                    BLTarjeta {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            perfil.resultados.takeLast(5).forEach { victoria ->
                                Surface(
                                    modifier = Modifier.size(30.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    color = if (victoria) {
                                        MaterialTheme.colorScheme.secondaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.errorContainer
                                    }
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = if (victoria) "V" else "D",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = if (victoria) {
                                                MaterialTheme.colorScheme.onSecondaryContainer
                                            } else {
                                                MaterialTheme.colorScheme.onErrorContainer
                                            }
                                        )
                                    }
                                }
                            }

                            Text(
                                text = "${perfil.resultados.takeLast(5).count { it }} " +
                                        "de ${perfil.resultados.takeLast(5).size}",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }

            item {
                BLTitulo("Juegos más jugados")
            }

            if (perfil.juegos.isEmpty()) {
                item {
                    BLVacio("Aún no hay juegos para mostrar.")
                }
            } else {
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(perfil.juegos) { juego ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                BLPortada(
                                    juego = juego,
                                    modifier = Modifier.size(88.dp)
                                )

                                Text(
                                    text = juego.nombre,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BLEstadisticaPerfil(
    titulo: String,
    valor: String,
    modifier: Modifier
) {
    BLTarjeta(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(
    name = "Perfil de jugador",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PerfilJugadorPreview() {
    PerfilJugadorRoute()
}