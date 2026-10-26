package com.example.boardlog.diseno

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.R
import com.example.boardlog.datos.JuegoMesa
import com.example.boardlog.navegacion.Destino

val ColorFondo = Color(0xFFFAF7F2)
val NaranjaBoardLog = Color(0xFFFF8528)
val VerdeBoardLog = Color(0xFF07564C)
val RojoBoardLog = Color(0xFFFF3636)

@Composable
fun PaginaBoardLog(
    title: String? = null,
    back: Boolean = false,
    barraInferior: Boolean = true,
    alVolver: () -> Unit = {},
    alNavegar: (Destino) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        containerColor = ColorFondo,
        topBar = {
            Row(Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 12.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                if (back) IconButton(onClick = alVolver) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver") }
                if (title == null) Image(painterResource(R.drawable.boardlog_logo), contentDescription = "BoardLog", modifier = Modifier.width(142.dp).height(50.dp), contentScale = ContentScale.Fit)
                else Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = VerdeBoardLog)
            }
        },
        bottomBar = { if (barraInferior) BarraInferior(alNavegar) }
    ) { inset ->
        Column(Modifier.fillMaxSize().padding(inset).padding(horizontal = 20.dp).padding(bottom = 12.dp), content = content)
    }
}

@Composable
fun BarraInferior(alNavegar: (Destino) -> Unit) {
    Surface(color = Color.White, shadowElevation = 3.dp) {
        Row(Modifier.fillMaxWidth().navigationBarsPadding().height(65.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { alNavegar(Destino.Inicio) }) { Icon(Icons.Default.Home, contentDescription = "Inicio") }
            IconButton(onClick = { alNavegar(Destino.Juegos) }) { Icon(Icons.Default.Casino, contentDescription = "Juegos") }
            FilledIconButton(onClick = { alNavegar(Destino.SeleccionJuego) }, colors = IconButtonDefaults.filledIconButtonColors(containerColor = NaranjaBoardLog)) { Icon(Icons.Default.Add, contentDescription = "Nueva partida", tint = Color.White) }
            IconButton(onClick = { alNavegar(Destino.Historial) }) { Icon(Icons.Default.History, contentDescription = "Historial") }
            IconButton(onClick = { alNavegar(Destino.Estadisticas) }) { Icon(Icons.Default.BarChart, contentDescription = "Estadísticas") }
        }
    }
}

@Composable
fun Encabezado(text: String) = Text(text, fontSize = 23.sp, color = VerdeBoardLog, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 10.dp))

@Composable
fun BarraBusqueda(value: String, placeholder: String, onChange: (String) -> Unit) {
    OutlinedTextField(value = value, onValueChange = onChange, placeholder = { Text(placeholder) }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), shape = RoundedCornerShape(15.dp))
}

@Composable
fun BotonPrincipal(label: String, enabled: Boolean = true, esPeligroso: Boolean = false, onClick: () -> Unit) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = if (esPeligroso) RojoBoardLog else NaranjaBoardLog, contentColor = Color.White)) { Text(label) }
}

@Composable
fun BotonContorno(label: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(10.dp)) { Text(label, color = NaranjaBoardLog) }
}

@Composable
fun PortadaJuego(juego: JuegoMesa, modifier: Modifier = Modifier) = Image(painter = painterResource(juego.imagen), contentDescription = juego.nombre, modifier = modifier, contentScale = ContentScale.Fit)

@Composable
fun TarjetaBoardLog(onClick: (() -> Unit)? = null, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(Modifier.fillMaxWidth().padding(12.dp), content = content)
    }
}

@Composable
fun ListaAplicacion(modifier: Modifier = Modifier, content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 18.dp), verticalArrangement = Arrangement.spacedBy(10.dp), content = content)
}
