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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.R

//colores
private val fondoResultados = Color(0xFFFAF7F2)
private val naranjaResultados = Color(0xFFE95F3D)
private val verdeResultados = Color(0xFF32885D)
private val grisResultados = Color(0xFFF0F0F0)
private val negroResultados = Color(0xFF1D1D1D)

//datos de cada jugador
data class ResultadoJugador(
    val posicion: Int,
    val nombre: String,
    val puntos: Int,
    val cambio: String
)

//datos de prueba
private val resultadosPrueba = listOf(
    ResultadoJugador(
        posicion = 1,
        nombre = "David",
        puntos = 25,
        cambio = "-8"
    ),
    ResultadoJugador(
        posicion = 2,
        nombre = "Jorge",
        puntos = 16,
        cambio = "+4"
    ),
    ResultadoJugador(
        posicion = 3,
        nombre = "Wilfred",
        puntos = 0,
        cambio = "-0"
    )
)

//route
@Composable
fun ResultadosRoute() {
    ResultadosScreen(
        ganador = "DAVID",
        resultados = resultadosPrueba
    )
}

//pantalla
@Composable
fun ResultadosScreen(
    ganador: String,
    resultados: List<ResultadoJugador>,
    modifier: Modifier = Modifier
) {

    Scaffold(
        containerColor = fondoResultados,
        bottomBar = {
            BarraResultados()
        }
    ) { espacio ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(horizontal = 22.dp)
        ) {

            EncabezadoResultados()

            Text(
                text = "Partida Finalizada - Resultados",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = negroResultados
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            GanadorResultados(
                nombre = ganador
            )

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            resultados.forEach { jugador ->
                FilaResultado(
                    jugador = jugador
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            BotonesResultados()
        }
    }
}

//logo
@Composable
private fun EncabezadoResultados() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.boardlog_logo
            ),
            contentDescription = "BoardLog",
            modifier = Modifier.width(125.dp)
        )
    }
}

//ganador
@Composable
private fun GanadorResultados(
    nombre: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Partida de Prueba: David",
            fontSize = 14.sp,
            color = negroResultados
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "🏆",
            fontSize = 42.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        AvatarResultado()

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Box(
            modifier = Modifier
                .width(200.dp)
                .background(
                    color = verdeResultados,
                    shape = RoundedCornerShape(2.dp)
                )
                .padding(vertical = 9.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "$nombre\nGANÓ",
                color = Color.White,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

//resultado de cada jugador
@Composable
private fun FilaResultado(
    jugador: ResultadoJugador
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = grisResultados,
                shape = RoundedCornerShape(7.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "${jugador.posicion}.",
            fontSize = 14.sp,
            color = negroResultados
        )

        Spacer(
            modifier = Modifier.width(5.dp)
        )

        AvatarResultado(
            tamano = 25
        )

        Spacer(
            modifier = Modifier.width(5.dp)
        )

        Text(
            text = jugador.nombre,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = negroResultados
        )

        Text(
            text = "${jugador.puntos}   ${jugador.cambio}",
            fontSize = 14.sp,
            color = negroResultados
        )
    }

    Spacer(
        modifier = Modifier.height(5.dp)
    )
}

//avatar
@Composable
private fun AvatarResultado(
    tamano: Int = 48
) {

    Box(
        modifier = Modifier
            .size(tamano.dp)
            .background(
                color = Color(0xFFE6E6E6),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size((tamano * 0.75).dp)
        )
    }
}

//botones
@Composable
private fun BotonesResultados() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = naranjaResultados
            ),
            shape = RoundedCornerShape(10.dp)
        ) {

            Text(
                text = "Finalizar Partida",
                color = Color.White,
                fontSize = 15.sp
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            border = BorderStroke(
                width = 1.dp,
                color = naranjaResultados
            ),
            shape = RoundedCornerShape(10.dp)
        ) {

            Text(
                text = "Volver al Inicio",
                color = naranjaResultados,
                fontSize = 15.sp
            )
        }
    }
}

//barra de abajo
@Composable
private fun BarraResultados() {

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
                    tint = negroResultados
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
                containerColor = Color(0xFFFF8528),
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
                    tint = negroResultados
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
    name = "Resultados",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewResultados() {

    ResultadosScreen(
        ganador = "DAVID",
        resultados = resultadosPrueba
    )
}