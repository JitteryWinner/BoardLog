package com.example.boardlog.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
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
private val fondo = Color(0xFFFAF7F2)
private val naranja = Color(0xFFFF8528)
private val verde = Color(0xFF00A85A)
private val rojoClaro = Color(0xFFD37B78)
private val borde = Color(0xFFD8D8D8)
private val negro = Color(0xFF1D1D1D)

//datos de las partidas
data class PartidaReciente(
    val nombre: String,
    val fecha: String,
    val jugadores: Int,
    val ganador: String,
    val imagen: Int
)

//datos de prueba
private val partidasPrueba = listOf(

    PartidaReciente(
        nombre = "Catan",
        fecha = "5 sep.",
        jugadores = 3,
        ganador = "User1 Ganó",
        imagen = R.drawable.catan
    ),

    PartidaReciente(
        nombre = "UNO",
        fecha = "3 sep.",
        jugadores = 2,
        ganador = "User2 Ganó",
        imagen = R.drawable.uno
    )
)

//route
@Composable
fun HomeRoute() {
    HomeScreen(
        nombreUsuario = "User",
        partidas = partidasPrueba
    )
}

//pantalla principal
@Composable
fun HomeScreen(
    nombreUsuario: String,
    partidas: List<PartidaReciente>,
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
                start = 20.dp,
                end = 20.dp,
                top = 18.dp,
                bottom = 24.dp
            ),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Encabezado()
            }

            item {
                Saludo(nombreUsuario)
            }

            item {
                BotonNuevaPartida()
            }

            item {
                TituloSeccion("Partida en progreso")
            }

            item {
                PartidaActual()
            }

            item {
                TituloSeccion("Actividad reciente")
            }

            items(partidas) { partida ->

                TarjetaPartida(
                    partida = partida
                )
            }

            item {
                Estadisticas()
            }
        }
    }
}

//logo y configuración
@Composable
private fun Encabezado() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.boardlog_logo
            ),
            contentDescription = "Logo",
            modifier = Modifier.width(165.dp)
        )

        IconButton(
            onClick = {}
        ) {

            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configuración",
                tint = negro,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

//saludo
@Composable
private fun Saludo(
    nombreUsuario: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {

        Text(
            text = "¡Hola, $nombreUsuario!",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = negro
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = "¿Qué vamos a jugar hoy?",
            fontSize = 18.sp,
            color = negro
        )
    }
}

//botón de nueva partida
@Composable
private fun BotonNuevaPartida() {

    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(57.dp),

        shape = RoundedCornerShape(10.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = naranja
        )
    ) {

        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = "Nueva Partida",
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

//títulos
@Composable
private fun TituloSeccion(
    titulo: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = titulo,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = negro
        )

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = negro,
            modifier = Modifier.size(30.dp)
        )
    }
}

//partida que sigue activa
@Composable
private fun PartidaActual() {

    Card(
        modifier = Modifier.fillMaxWidth(),

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
                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.risk
                ),
                contentDescription = "Risk",
                modifier = Modifier
                    .size(105.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Risk",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = negro
                )

                Text(
                    text = "Usuario1 - Usuario 2 - Usuario3",
                    fontSize = 13.sp,
                    color = negro
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = negro
                    )

                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )

                    Text(
                        text = "Iniciada Ayer",
                        fontSize = 13.sp,
                        color = negro
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Button(
                        onClick = {},

                        shape = RoundedCornerShape(8.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = rojoClaro
                        ),

                        modifier = Modifier.height(36.dp)
                    ) {

                        Text(
                            text = "Continuar",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

//partidas anteriores
@Composable
private fun TarjetaPartida(
    partida: PartidaReciente
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        border = BorderStroke(
            1.dp,
            borde
        ),

        shape = RoundedCornerShape(9.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    id = partida.imagen
                ),
                contentDescription = partida.nombre,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = partida.nombre,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = negro
                )

                Text(
                    text = "${partida.fecha} - ${partida.jugadores} jugadores",
                    fontSize = 13.sp,
                    color = negro
                )
            }

            Surface(
                shape = RoundedCornerShape(9.dp),
                color = verde
            ) {

                Text(
                    text = partida.ganador,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        horizontal = 9.dp,
                        vertical = 7.dp
                    )
                )
            }
        }
    }
}

//estadísticas
@Composable
private fun Estadisticas() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp),

        horizontalArrangement = Arrangement.spacedBy(8.dp)
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

//cada cuadro de estadística
@Composable
private fun CajaEstadistica(
    numero: String,
    texto: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(88.dp),

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
                fontSize = 19.sp,
                color = negro
            )

            Text(
                text = texto,
                fontSize = 16.sp,
                color = negro
            )
        }
    }
}

//barra de abajo
@Composable
private fun BarraInferior() {

    Surface(
        color = Color.White,
        shadowElevation = 5.dp
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
    name = "Home",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewHome() {

    HomeScreen(
        nombreUsuario = "User",
        partidas = partidasPrueba
    )
}