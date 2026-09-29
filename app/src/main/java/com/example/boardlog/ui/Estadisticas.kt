package com.example.boardlog.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.R

private val fondoEstadisticas = Color(0xFFFAF7F2)
private val verdeEstadisticas = Color(0xFF07564C)
private val naranjaEstadisticas = Color(0xFFFF7A2F)
private val grisEstadisticas = Color(0xFFF0F0F0)

@Composable
fun EstadisticasScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = fondoEstadisticas,
        bottomBar = {
            BarraEstadisticas()
        }
    ) { espacio ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(espacio)
                .padding(horizontal = 18.dp)
        ) {

            LogoEstadisticas()

            Text(
                text = "Mis Estadísticas",
                color = verdeEstadisticas,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            FiltrosEstadisticas()

            Spacer(modifier = Modifier.height(12.dp))

            ResumenGeneral()

            Spacer(modifier = Modifier.height(12.dp))

            MejoresJuegos()

            Spacer(modifier = Modifier.height(12.dp))

            RachaVictorias()
        }
    }
}

@Composable
private fun LogoEstadisticas() {
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

@Composable
private fun FiltrosEstadisticas() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {

        Filtro(
            texto = "Este Mes",
            modifier = Modifier.weight(1f)
        )

        Filtro(
            texto = "Este Año",
            modifier = Modifier.weight(1f)
        )

        Filtro(
            texto = "Todo",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun Filtro(
    texto: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(38.dp),
        color = grisEstadisticas,
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = texto,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ResumenGeneral() {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = "Resumen General",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                EstadisticaResumen(
                    numero = "24",
                    texto = "🎲 Partidas\nJugadas"
                )

                EstadisticaResumen(
                    numero = "41%",
                    texto = "🏆 Porcentaje de\nVictorias"
                )
            }
        }
    }
}

@Composable
private fun EstadisticaResumen(
    numero: String,
    texto: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = numero,
            color = verdeEstadisticas,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = texto,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 12.sp
        )
    }
}

@Composable
private fun MejoresJuegos() {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = "M i s   M e j o r e s   J u e g o s",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                JuegoEstadistica(
                    nombre = "Risk",
                    victorias = "13 Vict.",
                    porcentaje = "65%",
                    imagen = R.drawable.risk,
                    modifier = Modifier.weight(1f)
                )

                JuegoEstadistica(
                    nombre = "Catan",
                    victorias = "6 Vict.",
                    porcentaje = "30%",
                    imagen = R.drawable.catan,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                JuegoEstadistica(
                    nombre = "Ticket to Ride",
                    victorias = "3 Vict.",
                    porcentaje = "15%",
                    imagen = R.drawable.risk,
                    modifier = Modifier.weight(1f)
                )

                JuegoEstadistica(
                    nombre = "UNO",
                    victorias = "2 Vict.",
                    porcentaje = "10%",
                    imagen = R.drawable.uno,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun JuegoEstadistica(
    nombre: String,
    victorias: String,
    porcentaje: String,
    imagen: Int,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = imagen),
            contentDescription = nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column {

            Text(
                text = nombre,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "▣ $victorias",
                fontSize = 10.sp
            )

            Text(
                text = "◷ $porcentaje",
                fontSize = 10.sp
            )
        }
    }
}

@Composable
private fun RachaVictorias() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(185.dp),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = "R a c h a   d e   V i c t o r i a s",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "3",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            GraficaVictorias(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "0",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "F e c h a",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun GraficaVictorias(
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
    ) {

        val ancho = size.width
        val alto = size.height

        drawLine(
            color = Color.LightGray,
            start = Offset(0f, alto),
            end = Offset(ancho, alto),
            strokeWidth = 1f
        )

        val path = Path().apply {

            moveTo(
                0f,
                alto * 0.78f
            )

            cubicTo(
                ancho * 0.10f,
                alto * 0.75f,
                ancho * 0.15f,
                alto * 0.30f,
                ancho * 0.28f,
                alto * 0.35f
            )

            cubicTo(
                ancho * 0.40f,
                alto * 0.40f,
                ancho * 0.42f,
                alto * 0.80f,
                ancho * 0.55f,
                alto * 0.72f
            )

            cubicTo(
                ancho * 0.65f,
                alto * 0.65f,
                ancho * 0.65f,
                alto * 0.30f,
                ancho * 0.77f,
                alto * 0.35f
            )

            cubicTo(
                ancho * 0.88f,
                alto * 0.40f,
                ancho * 0.90f,
                alto * 0.70f,
                ancho,
                alto * 0.62f
            )
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 3f
            )
        )
    }
}

@Composable
private fun BarraEstadisticas() {

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
                containerColor = naranjaEstadisticas,
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

@Preview(
    name = "Estadísticas",
    showBackground = true,
    widthDp = 390,
    heightDp = 844
)
@Composable
private fun PreviewEstadisticas() {

    EstadisticasScreen()
}