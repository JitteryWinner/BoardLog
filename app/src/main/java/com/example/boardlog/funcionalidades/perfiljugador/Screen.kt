package com.example.boardlog.funcionalidades.perfiljugador

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

@Composable fun PantallaPerfilJugador(repositorio:RepositorioBoardLog,idJugador:String,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:PerfilJugadorViewModel=viewModel(factory=FabricaViewModel{PerfilJugadorViewModel(repositorio,idJugador)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Perfil de jugador",back=true,alVolver=alVolver,alNavegar={modeloVista.alEvento(EventoPerfilJugador.Abrir(it))}) {
    val p=estadoPantalla.jugador
    if(p==null){Text("Jugador no encontrado");return@PaginaBoardLog}
    Encabezado(p.nombre)
    val finalizadas=estadoPantalla.completadas;val victorias=estadoPantalla.victorias
    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly) { ProfileMetric("${estadoPantalla.partidas.size}","PARTIDAS");ProfileMetric("$victorias","VICTORIAS");ProfileMetric("${estadoPantalla.porcentaje}%","% VICTORIAS") }
    Spacer(Modifier.height(20.dp));Text("Rendimiento",fontWeight=FontWeight.Bold,fontSize=19.sp)
    Text("$victorias victorias en total · Últimas ${finalizadas.size.coerceAtMost(5)} partidas")
    Row { finalizadas.take(5).forEach {partida->Text(if(partida.idGanador()==p.id)"🟢" else "🔴",fontSize=26.sp)} }
    Spacer(Modifier.height(16.dp));Text("Juegos más jugados",fontWeight=FontWeight.Bold,fontSize=19.sp)
    ListaAplicacion { val juegos=estadoPantalla.juegosMasJugados;items(juegos) { item -> val g=item.juego;TarjetaBoardLog(onClick={modeloVista.alEvento(EventoPerfilJugador.Abrir(Destino.InformacionJuego(g.id)))}) { Row(verticalAlignment=Alignment.CenterVertically) {PortadaJuego(g,Modifier.size(46.dp));Spacer(Modifier.width(12.dp));Text("${g.nombre} · ${item.cantidad} partidas")}} } }
 }
}
@Composable private fun ProfileMetric(value:String,label:String){Column(horizontalAlignment=Alignment.CenterHorizontally){Text(value,fontSize=22.sp,fontWeight=FontWeight.Bold,color=VerdeBoardLog);Text(label,fontSize=11.sp)}}
