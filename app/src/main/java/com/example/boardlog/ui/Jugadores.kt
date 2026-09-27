package com.example.boardlog.ui.screens.jugadores

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
private val amarillo = Color(0xFFFFB900)
private val gris = Color(0xFF737B83)

//datos de jugadores
data class Jugador(
    val nombre: String,
    val partidas: Int,
    val victorias: Int
)

//datos de prueba
private val jugadoresPrueba = listOf(

    Jugador(
        nombre = "David",
        partidas = 24,
        victorias = 3
    ),

    Jugador(
        nombre = "Jorge",
        partidas = 5,
        victorias = 0
    ),

    Jugador(
        nombre = "Wilfred",
        partidas = 16,
        victorias = 2
    ),

    Jugador(
        nombre = "User 4",
        partidas = 12,
        victorias = 6
    ),

    Jugador(
        nombre = "User 5",
        partidas = 7,
        victorias = 1
    )
)

//route
@Composable
fun JugadoresRoute() {

    JugadoresScreen(
        jugadores = jugadoresPrueba
    )
}

//pantalla
@Composable
fun JugadoresScreen(
    jugadores: List<Jugador>,
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
                start = 26.dp,
                end = 26.dp,
                top = 16.dp,
                bottom = 30.dp
            ),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                Encabezado()
            }

            item {

                Text(
                    text = "Jugadores",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = negro
                )
            }

            item {
                Pestañas()
            }

            item {
                Buscador()
            }

            item {
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

            items(jugadores) { jugador ->

                TarjetaJugador(
                    jugador = jugador
                )
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

//pestañas
@Composable
private fun Pestañas() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

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
                text = "Juegos",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

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
                text = "Jugadores",
                color = naranja,
                fontSize = 16.sp
            )
        }
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
                text = "Buscar Jugador",
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

//tarjeta del jugador
@Composable
private fun TarjetaJugador(
    jugador: Jugador
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(79.dp),

        shape = RoundedCornerShape(8.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        border = BorderStroke(
            1.dp,
            borde
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Avatar()

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = jugador.nombre,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = negro
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "${jugador.partidas} partidas - ${jugador.victorias} victorias",
                    fontSize = 13.sp,
                    color = negro
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ver jugador",
                tint = negro,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

//avatar
@Composable
private fun Avatar() {

    Box(
        modifier = Modifier
            .size(58.dp)
            .background(
                color = amarillo,
                shape = CircleShape
            ),

        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = gris,
            modifier = Modifier.size(46.dp)
        )
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
    name = "Jugadores",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewJugadores() {

    JugadoresScreen(
        jugadores = jugadoresPrueba
    )
}