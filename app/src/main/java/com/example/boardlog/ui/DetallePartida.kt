package com.example.boardlog.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
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
private val fondoDetalle = Color(0xFFFAF7F2)
private val naranjaDetalle = Color(0xFFE95F3D)
private val verdeDetalle = Color(0xFF32885D)
private val negroDetalle = Color(0xFF1D1D1D)
private val grisDetalle = Color(0xFFE6E6E6)

//datos de jugador
data class JugadorDetalle(
    val nombre: String,
    val puntos: Int,
    val posicion: Int,
    val ganador: Boolean = false
)

//datos de prueba
private val jugadoresDetallePrueba = listOf(
    JugadorDetalle(
        nombre = "David",
        puntos = 10,
        posicion = 1,
        ganador = true
    ),
    JugadorDetalle(
        nombre = "Jorge",
        puntos = 8,
        posicion = 2
    ),
    JugadorDetalle(
        nombre = "Wilfred",
        puntos = 6,
        posicion = 3
    ),
    JugadorDetalle(
        nombre = "Ana",
        puntos = 4,
        posicion = 4
    )
)

//route
@Composable
fun DetallePartidaRoute() {

    DetallePartidaScreen(
        nombreJuego = "Catan",
        nombrePartida = "Partida Épica",
        jugadores = jugadoresDetallePrueba
    )
}

//pantalla
@Composable
fun DetallePartidaScreen(
    nombreJuego: String,
    nombrePartida: String,
    jugadores: List<JugadorDetalle>,
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = fondoDetalle,
        bottomBar = {
            BarraDetallePartida()
        }
    ) { espacio ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(horizontal = 18.dp)
        ) {

            EncabezadoDetalle()

            ImagenJuegoDetalle()

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Detalle de Partida:",
                color = negroDetalle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$nombreJuego - $nombrePartida",
                color = negroDetalle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            TitulosTablaDetalle()

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            jugadores.forEach { jugador ->
                FilaJugadorDetalle(
                    jugador = jugador
                )
            }

            Spacer(
                modifier = Modifier.height(13.dp)
            )

            HorizontalDivider(
                color = grisDetalle
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            GanadorDetalle(
                nombre = jugadores.firstOrNull {
                    it.ganador
                }?.nombre ?: "David"
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            BotonCompartirDetalle()
        }
    }
}

//logo
@Composable
private fun EncabezadoDetalle() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.boardlog_logo
            ),
            contentDescription = "BoardLog",
            modifier = Modifier.width(115.dp)
        )
    }
}

//imagen de catan
@Composable
private fun ImagenJuegoDetalle() {

    Image(
        painter = painterResource(
            id = R.drawable.catan
        ),
        contentDescription = "Catan",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(
                RoundedCornerShape(12.dp)
            )
    )
}

//titulos
@Composable
private fun TitulosTablaDetalle() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Jugador",
            modifier = Modifier.weight(1f),
            color = negroDetalle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Pts",
            modifier = Modifier.width(45.dp),
            color = negroDetalle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Posición",
            modifier = Modifier.width(55.dp),
            color = negroDetalle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//fila de jugador
@Composable
private fun FilaJugadorDetalle(
    jugador: JugadorDetalle
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(37.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AvatarDetalle(
            tamano = 25
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(
            text = jugador.nombre,
            modifier = Modifier.weight(1f),
            color = negroDetalle,
            fontSize = 13.sp
        )

        Row(
            modifier = Modifier.width(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = jugador.puntos.toString(),
                color = negroDetalle,
                fontSize = 13.sp
            )

            if (jugador.ganador) {

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = "🏆",
                    fontSize = 11.sp
                )
            }
        }

        Text(
            text = jugador.posicion.toString(),
            modifier = Modifier.width(55.dp),
            color = negroDetalle,
            fontSize = 13.sp
        )
    }
}

//ganador
@Composable
private fun GanadorDetalle(
    nombre: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "Ganador de la Partida",
                color = negroDetalle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = nombre,
                color = negroDetalle,
                fontSize = 14.sp
            )
        }

        AvatarDetalle(
            tamano = 55
        )
    }
}

//avatar
@Composable
private fun AvatarDetalle(
    tamano: Int
) {

    Box(
        modifier = Modifier
            .size(tamano.dp)
            .background(
                color = grisDetalle,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(
                (tamano * 0.7).dp
            )
        )
    }
}

//boton compartir
@Composable
private fun BotonCompartirDetalle() {

    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = naranjaDetalle
        ),
        shape = RoundedCornerShape(12.dp)
    ) {

        Text(
            text = "Compartir Resultado",
            color = Color.White,
            fontSize = 15.sp
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Compartir",
            tint = Color.Black
        )
    }
}

//barra inferior
@Composable
private fun BarraDetallePartida() {

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
                    modifier = Modifier.size(29.dp),
                    tint = negroDetalle
                )
            }

            IconButton(
                onClick = {}
            ) {

                Text(
                    text = "🎲",
                    fontSize = 26.sp
                )
            }

            FloatingActionButton(
                onClick = {},
                containerColor = Color(0xFFFF8528),
                contentColor = Color.White,
                modifier = Modifier.size(54.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nueva partida",
                    modifier = Modifier.size(30.dp)
                )
            }

            IconButton(
                onClick = {}
            ) {

                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Historial",
                    modifier = Modifier.size(29.dp),
                    tint = negroDetalle
                )
            }

            IconButton(
                onClick = {}
            ) {

                Text(
                    text = "📊",
                    fontSize = 24.sp
                )
            }
        }
    }
}

//preview
@Preview(
    name = "Detalle de Partida",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewDetallePartida() {

    DetallePartidaScreen(
        nombreJuego = "Catan",
        nombrePartida = "Partida Épica",
        jugadores = jugadoresDetallePrueba
    )
}