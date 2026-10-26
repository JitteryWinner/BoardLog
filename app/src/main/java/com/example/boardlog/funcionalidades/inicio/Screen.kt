package com.example.boardlog.funcionalidades.inicio

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.diseno.*
import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect

@Composable
fun PantallaInicio(repositorio: RepositorioBoardLog, irA: (Destino) -> Unit) {
    val modeloVista: InicioViewModel = viewModel(factory=FabricaViewModel { InicioViewModel(repositorio) })
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()
    LaunchedEffect(modeloVista) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(alNavegar={ modeloVista.alEvento(EventoInicio.Abrir(it)) }) {
      Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Text("¡Hola, ${estadoPantalla.nombreUsuario}!", fontSize=23.sp, fontWeight=FontWeight.Bold)
        Text("¿Qué vamos a jugar hoy?", color=VerdeBoardLog)
        Spacer(Modifier.height(14.dp))
        BotonPrincipal("＋ Nueva Partida") { modeloVista.alEvento(EventoInicio.Abrir(Destino.SeleccionJuego)) }
        Spacer(Modifier.height(20.dp))
        Row(verticalAlignment=Alignment.CenterVertically, modifier=Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween) {
            Text("Partida en progreso", fontWeight=FontWeight.Bold, fontSize=18.sp)
            IconButton(onClick={modeloVista.alEvento(EventoInicio.Abrir(Destino.Historial))}) { Icon(Icons.Default.ArrowForward, null) }
        }
        val partidaEnCurso=estadoPantalla.partidas.firstOrNull { it.estadoPartida == ESTADO_EN_CURSO }
        if(partidaEnCurso == null) Text("No tienes partidas en progreso.") else {
            val g=CatalogoJuegos.obtener(partidaEnCurso.idJuego)
            TarjetaBoardLog(onClick={modeloVista.alEvento(EventoInicio.Abrir(Destino.PartidaEnCurso(partidaEnCurso.id)))}) {
                Row(verticalAlignment=Alignment.CenterVertically) {
                    if(g!=null) PortadaJuego(g,Modifier.size(76.dp))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) { Text(g?.nombre?:"Juego",fontWeight=FontWeight.Bold); Text("${partidaEnCurso.ids().size} jugadores · En progreso") }
                    Text("Continuar",color=NaranjaBoardLog)
                }
            }
        }
        Spacer(Modifier.height(18.dp))
        Text("Actividad reciente", fontSize=18.sp, fontWeight=FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        estadoPantalla.partidas.filter { it.estadoPartida==ESTADO_FINALIZADO }.take(3).forEach { partida ->
            val g=CatalogoJuegos.obtener(partida.idJuego)
            TarjetaBoardLog(onClick={modeloVista.alEvento(EventoInicio.Abrir(Destino.DetallePartida(partida.id)))}) {
                Row(verticalAlignment=Alignment.CenterVertically) {
                    if(g!=null) PortadaJuego(g,Modifier.size(55.dp))
                    Column(Modifier.padding(start=10.dp).weight(1f)) { Text(g?.nombre?:"Juego",fontWeight=FontWeight.Bold); Text("${partida.ids().size} jugadores") }
                    Text("Ver",color=VerdeBoardLog)
                }
            }
            Spacer(Modifier.height(5.dp))
        }
        Spacer(Modifier.height(18.dp))
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly) {
            MetricaInicio("${estadoPantalla.partidas.size}","Partidas"); MetricaInicio("${CatalogoJuegos.juegos.size}","Juegos"); MetricaInicio("${estadoPantalla.porcentajeVictorias}%","Victorias (ejemplo)")
        }
    }
    }
}
@Composable private fun MetricaInicio(numero:String,label:String) { Column(horizontalAlignment=Alignment.CenterHorizontally) { Text(numero,fontWeight=FontWeight.Bold,fontSize=22.sp,color=VerdeBoardLog); Text(label,fontSize=11.sp) } }
