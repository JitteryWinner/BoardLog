package com.example.boardlog.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.R

//colores
private val fondoHistorial = Color(0xFFFAF7F2)
private val verdeHistorial = Color(0xFF07564C)
private val naranjaHistorial = Color(0xFFFF7A2F)
private val grisHistorial = Color(0xFFF1F1F1)
private val verdeGanador = Color(0xFFE7F4D5)

//datos
data class PartidaHistorial(
    val juego: String,
    val fecha: String,
    val jugadores: String,
    val tiempo: String,
    val ganador: String,
    val imagen: Int
)

//datos de prueba
private val partidasPrueba = listOf(
    PartidaHistorial(
        juego = "Risk",
        fecha = "2 sep",
        jugadores = "David, Jorge, Wilfred",
        tiempo = "1h 45m",
        ganador = "David",
        imagen = R.drawable.risk
    ),
    PartidaHistorial(
        juego = "Catan",
        fecha = "4 sep",
        jugadores = "Ana, David, Jorge, Sara",
        tiempo = "1h 35m",
        ganador = "Ana",
        imagen = R.drawable.catan
    ),
    PartidaHistorial(
        juego = "Ticket to Ride",
        fecha = "3 sep",
        jugadores = "David, Jorge",
        tiempo = "",
        ganador = "Jorge",
        imagen = R.drawable.risk
    )
)

//pantalla
@Composable
fun HistorialScreen(
    partidas: List<PartidaHistorial> = partidasPrueba,
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = fondoHistorial,
        bottomBar = {
            BarraHistorial()
        }
    ) { espacio ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(horizontal = 18.dp)
        ) {

            LogoHistorial()

            Text(
                text = "Historial",
                color = verdeHistorial,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            BuscadorHistorial()

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            partidas.forEach { partida ->

                TarjetaHistorial(
                    partida = partida
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }
        }
    }
}

//logo
@Composable
private fun LogoHistorial() {

    Image(
        painter = painterResource(
            id = R.drawable.boardlog_logo
        ),
        contentDescription = "BoardLog",
        modifier = Modifier
            .width(110.dp)
            .padding(
                top = 10.dp,
                bottom = 5.dp
            )
    )
}

//buscador
@Composable
private fun BuscadorHistorial() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(12.dp),
        color = grisHistorial
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            Text(
                text = "Buscar",
                color = Color.Gray,
                fontSize = 13.sp
            )
        }
    }
}

//tarjeta
@Composable
private fun TarjetaHistorial(
    partida: PartidaHistorial
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Image(
                painter = painterResource(
                    id = partida.imagen
                ),
                contentDescription = partida.juego,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(105.dp)
                    .clip(
                        RoundedCornerShape(9.dp)
                    )
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 2.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = partida.juego,
                        modifier = Modifier.weight(1f),
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        color = verdeGanador,
                        shape = RoundedCornerShape(4.dp)
                    ) {

                        Text(
                            text = "🏆 ${partida.ganador} ganó",
                            modifier = Modifier.padding(
                                horizontal = 6.dp,
                                vertical = 3.dp
                            ),
                            color = Color.Black,
                            fontSize = 9.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "▣ ${partida.fecha}",
                    fontSize = 11.sp,
                    color = Color.Black
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "👥 ${partida.jugadores}",
                    fontSize = 11.sp,
                    color = Color.Black
                )

                if (partida.tiempo.isNotEmpty()) {

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "◷ Tiempo: ${partida.tiempo}",
                        fontSize = 11.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "▣ Fecha: Hoy",
                    fontSize = 11.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

//barra inferior
@Composable
private fun BarraHistorial() {

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
                    modifier = Modifier.size(28.dp),
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = {}
            ) {

                Text(
                    text = "🎲",
                    fontSize = 25.sp
                )
            }

            FloatingActionButton(
                onClick = {},
                containerColor = naranjaHistorial,
                contentColor = Color.White,
                modifier = Modifier.size(52.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    modifier = Modifier.size(29.dp)
                )
            }

            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Historial",
                    modifier = Modifier.size(29.dp),
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = {}
            ) {

                Text(
                    text = "📊",
                    fontSize = 23.sp
                )
            }
        }
    }
}

//preview
@Preview(
    name = "Historial",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewHistorial() {

    HistorialScreen()
}