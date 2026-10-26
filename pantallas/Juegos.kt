package com.example.boardlog.ui.screens.juegos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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

//colores
private val fondo = Color(0xFFFAF7F2)
private val naranja = Color(0xFFFF8528)
private val borde = Color(0xFFD8D8D8)
private val negro = Color(0xFF1D1D1D)

//datos de juegos
data class Juego(
    val nombre: String,
    val partidas: Int,
    val imagen: Int,
    val favorito: Boolean = false
)

//datos de prueba
private val juegosPrueba = listOf(

    Juego(
        nombre = "Catan",
        partidas = 12,
        imagen = R.drawable.catan,
        favorito = true
    ),

    Juego(
        nombre = "UNO",
        partidas = 6,
        imagen = R.drawable.uno,
        favorito = true
    ),

    Juego(
        nombre = "Risk",
        partidas = 16,
        imagen = R.drawable.risk,
        favorito = true
    ),

    Juego(
        nombre = "Clue",
        partidas = 4,
        imagen = R.drawable.clue,
        favorito = true
    ),

    Juego(
        nombre = "Monopoly",
        partidas = 8,
        imagen = R.drawable.monopoly,
        favorito = true
    ),

    Juego(
        nombre = "Ajedrez",
        partidas = 10,
        imagen = R.drawable.ajedrez,
        favorito = true
    )
)

//route
@Composable
fun JuegosRoute() {

    JuegosScreen(
        juegos = juegosPrueba
    )
}

//pantalla
@Composable
fun JuegosScreen(
    juegos: List<Juego>,
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = fondo,

        bottomBar = {
            BarraInferior()
        }

    ) { espacio ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(espacio),

            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 24.dp
            ),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                Encabezado()
            }

            item {
                Buscador()
            }

            item {
                Pestañas()
            }

            item {
                Filtros()
            }

            items(
                juegos.chunked(2)
            ) { filaJuegos ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    filaJuegos.forEach { juego ->

                        TarjetaJuego(
                            juego = juego,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (filaJuegos.size == 1) {

                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

//logo
@Composable
private fun Encabezado() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.boardlog_logo
            ),
            contentDescription = "BoardLog",
            modifier = Modifier.width(155.dp)
        )
    }
}

//buscador
@Composable
private fun Buscador() {

    TextField(
        value = "",
        onValueChange = {},

        placeholder = {

            Text(
                text = "Buscar Juego",
                fontSize = 16.sp
            )
        },

        trailingIcon = {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar"
            )
        },

        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),

        singleLine = true,

        shape = RoundedCornerShape(25.dp),

        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

//pestañas de juegos y jugadores
@Composable
private fun Pestañas() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedButton(
            onClick = {},

            modifier = Modifier
                .weight(1f)
                .height(48.dp),

            border = BorderStroke(
                1.dp,
                naranja
            ),

            shape = RoundedCornerShape(10.dp),

            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            )
        ) {

            Text(
                text = "Juegos",
                color = naranja,
                fontSize = 16.sp
            )
        }

        Button(
            onClick = {},

            modifier = Modifier
                .weight(1f)
                .height(48.dp),

            shape = RoundedCornerShape(10.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = naranja
            )
        ) {

            Text(
                text = "Jugadores",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//filtros
@Composable
private fun Filtros() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(
            onClick = {},

            shape = RoundedCornerShape(9.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = naranja
            ),

            contentPadding = PaddingValues(
                horizontal = 18.dp,
                vertical = 5.dp
            )
        ) {

            Text(
                text = "Todos",
                fontSize = 13.sp,
                color = Color.White
            )
        }

        BotonFiltro("Favoritos")

        BotonFiltro("Más Jugados")

        BotonFiltro("Pendientes")
    }
}

//botón de filtro
@Composable
private fun BotonFiltro(
    texto: String
) {

    OutlinedButton(
        onClick = {},

        shape = RoundedCornerShape(9.dp),

        border = BorderStroke(
            1.dp,
            borde
        ),

        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 5.dp
        )
    ) {

        Text(
            text = texto,
            fontSize = 12.sp,
            color = negro
        )
    }
}

//tarjeta de cada juego
@Composable
private fun TarjetaJuego(
    juego: Juego,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box {

            Image(
                painter = painterResource(
                    id = juego.imagen
                ),
                contentDescription = juego.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp),
                contentScale = ContentScale.Crop
            )

            Icon(
                imageVector = if (juego.favorito) {
                    Icons.Filled.Star
                } else {
                    Icons.Outlined.StarBorder
                },

                contentDescription = "Favorito",
                tint = negro,

                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
                    .size(28.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(50)
                    )
                    .padding(3.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp),

            shape = RoundedCornerShape(8.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            border = BorderStroke(
                1.dp,
                borde
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp,
                        vertical = 7.dp
                    )
            ) {

                Text(
                    text = juego.nombre,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = negro
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "🎲",
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.width(3.dp)
                    )

                    Text(
                        text = "${juego.partidas} partidas",
                        fontSize = 12.sp,
                        color = negro
                    )
                }
            }
        }
    }
}

//barra inferior
@Composable
private fun BarraInferior() {

    Surface(
        color = Color.White,
        shadowElevation = 4.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .padding(horizontal = 14.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio",
                    modifier = Modifier.size(30.dp),
                    tint = negro
                )
            }

            IconButton(
                onClick = {}
            ) {

                Text(
                    text = "🎲",
                    fontSize = 27.sp
                )
            }

            FloatingActionButton(
                onClick = {},
                containerColor = naranja,
                contentColor = Color.White,
                modifier = Modifier.size(56.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nueva partida",
                    modifier = Modifier.size(31.dp)
                )
            }

            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Historial",
                    modifier = Modifier.size(30.dp),
                    tint = negro
                )
            }

            IconButton(
                onClick = {}
            ) {

                Text(
                    text = "📊",
                    fontSize = 25.sp
                )
            }
        }
    }
}

//preview
@Preview(
    name = "Juegos",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewJuegos() {

    JuegosScreen(
        juegos = juegosPrueba
    )
}