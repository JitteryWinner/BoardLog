package com.example.boardlog.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
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
private val verde = Color(0xFF07564C)
private val fondoBarra = Color(0xFFE1DFDA)

//pantalla splash
@Composable
fun Splash(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(fondo)
    ) {

        //logo y texto
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-45).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.boardlog_logo
                ),
                contentDescription = "Logo BoardLog",
                modifier = Modifier.width(280.dp)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Tu historial de juegos, en un solo lugar",
                color = verde,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

        //barra de carga
        LinearProgressIndicator(
            progress = { 0.48f },
            modifier = Modifier
                .width(120.dp)
                .height(5.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 170.dp),
            color = verde,
            trackColor = fondoBarra
        )
    }
}

//preview
@Preview(
    name = "Splash",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SplashPreview() {
    Splash()
}