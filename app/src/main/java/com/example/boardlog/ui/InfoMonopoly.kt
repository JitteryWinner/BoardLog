package com.example.boardlog.ui.screens.info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.R

//colores
private val fondo = Color(0xFFFAF7F2)
private val naranja = Color(0xFFFF8528)
private val verde = Color(0xFF00A85A)
private val borde = Color(0xFFD8D8D8)
private val negro = Color(0xFF1D1D1D)

//datos de partidas
data class PartidaJuego(
    val fecha: String,
    val jugadores: Int,
    val ganador: String
)

//datos de prueba
private val partidasMonopoly = listOf(
    PartidaJuego(
        fecha = "5 sep.",
        jugadores = 3,
        ganador = "User1 Ganó"
    ),

    PartidaJuego(
        fecha = "3 sep.",
        jugadores = 2,
        ganador = "User2 Ganó"
    )
)

//route
@Composable
fun InfoMonopolyRoute() {

    InfoMonopolyScreen(
        titulo = "Monopoly",
        jugadores = "2-6 Jugadores",
        duracion = "60-120 min",
        categoria = "Estrategia",
        descripcion = "Capitaliza y construye tu propio monopolio, compite contra otros jugadores comprando negocios y propiedades.",
        partidas = partidasMonopoly
    )
}

//pantalla
@Composable
fun InfoMonopolyScreen(
    titulo: String,
    jugadores: String,
    duracion: String,
    categoria: String,
    descripcion: String,
    partidas: List<PartidaJuego>,
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
                bottom = 24.dp
            )
        ) {

            item {
                Encabezado()
            }

            item {
                ImagenGrande()
            }

            item {
                InfoJuego(
                    titulo = titulo,
                    jugadores = jugadores,
                    duracion = duracion,
                    categoria = categoria
                )
            }

            item {
                Descripcion(
                    descripcion = descripcion
                )
            }

            item {
                Estadisticas()
            }

            item {

                Text(
                    text = "Partidas Recientes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = negro,
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 14.dp,
                        bottom = 8.dp
                    )
                )
            }

            items(partidas.size) { numero ->

                TarjetaPartida(
                    partida = partidas[numero]
                )
            }

            item {
                Botones()
            }
        }
    }
}

//logo
@Composable
private fun Encabezado() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 16.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.boardlog_logo
            ),
            contentDescription = "BoardLog",
            modifier = Modifier.width(150.dp)
        )
    }
}

//imagen grande
@Composable
private fun ImagenGrande() {

    Image(
        painter = painterResource(
            id = R.drawable.monopoly_banner
        ),
        contentDescription = "Monopoly",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}

//información del juego
@Composable
private fun InfoJuego(
    titulo: String,
    jugadores: String,
    duracion: String,
    categoria: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier.size(128.dp),
            shape = RoundedCornerShape(9.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(
                        id = R.drawable.monopoly
                    ),
                    contentDescription = titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = negro
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = jugadores,
                fontSize = 14.sp,
                color = negro
            )

            Text(
                text = duracion,
                fontSize = 14.sp,
                color = negro
            )

            Text(
                text = categoria,
                fontSize = 14.sp,
                color = negro
            )
        }

        Text(
            text = "🎲",
            fontSize = 58.sp
        )
    }
}

//descripción
@Composable
private fun Descripcion(
    descripcion: String
) {

    Text(
        text = descripcion,
        fontSize = 14.sp,
        color = negro,
        lineHeight = 18.sp,
        modifier = Modifier.padding(
            horizontal = 10.dp,
            vertical = 4.dp
        )
    )
}

//estadísticas
@Composable
private fun Estadisticas() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
            )
    ) {

        Text(
            text = "Tus estadísticas",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = negro
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp),

            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {

            CajaEstadistica(
                numero = "24",
                texto = "Partidas",
                modifier = Modifier.weight(1f)
            )

            CajaEstadistica(
                numero = "8",
                texto = "Juegos",
                modifier = Modifier.weight(1f)
            )

            CajaEstadistica(
                numero = "41%",
                texto = "Victorias",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

//caja de estadística
@Composable
private fun CajaEstadistica(
    numero: String,
    texto: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(82.dp),

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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = numero,
                fontSize = 18.sp,
                color = negro
            )

            Text(
                text = texto,
                fontSize = 16.sp,
                color = negro,
                textAlign = TextAlign.Center
            )
        }
    }
}

//partida reciente
@Composable
private fun TarjetaPartida(
    partida: PartidaJuego
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),

        shape = RoundedCornerShape(9.dp),

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
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                ),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "${partida.fecha} - ${partida.jugadores} jugadores",
                fontSize = 13.sp,
                color = negro
            )

            Surface(
                color = verde,
                shape = RoundedCornerShape(12.dp)
            ) {

                Text(
                    text = partida.ganador,
                    fontSize = 11.sp,
                    color = Color.White,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 3.dp
                    )
                )
            }
        }
    }
}

//botones
@Composable
private fun Botones() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 10.dp
            ),

        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = naranja
            ),

            shape = RoundedCornerShape(9.dp)
        ) {

            Text(
                text = "Iniciar Partida",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),

            border = BorderStroke(
                1.dp,
                naranja
            ),

            shape = RoundedCornerShape(9.dp)
        ) {

            Text(
                text = "Ver historial",
                fontSize = 16.sp,
                color = naranja
            )
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
    name = "Info Monopoly",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewInfoMonopoly() {

    InfoMonopolyScreen(
        titulo = "Monopoly",
        jugadores = "2-6 Jugadores",
        duracion = "60-120 min",
        categoria = "Estrategia",
        descripcion = "Capitaliza y construye tu propio monopolio, compite contra otros jugadores comprando negocios y propiedades.",
        partidas = partidasMonopoly
    )
}