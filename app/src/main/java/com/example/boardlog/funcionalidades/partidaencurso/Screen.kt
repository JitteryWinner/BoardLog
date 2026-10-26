package com.example.boardlog.funcionalidades.partidaencurso

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

@Composable fun PantallaPartidaEnCurso(repositorio:RepositorioBoardLog,idPartida:String,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:PartidaEnCursoViewModel=viewModel(factory=FabricaViewModel{PartidaEnCursoViewModel(repositorio,idPartida)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Partida en progreso",back=true,barraInferior=false,alVolver=alVolver) {
  val m=estadoPantalla.partida
  if(m==null){Text("Cargando partida...");return@PaginaBoardLog}
  val g=estadoPantalla.juego
  Spacer(Modifier.height(12.dp));if(g!=null)TarjetaBoardLog {Row(verticalAlignment=Alignment.CenterVertically){PortadaJuego(g,Modifier.size(100.dp));Spacer(Modifier.width(12.dp));Column{Text(g.nombre,fontWeight=FontWeight.Bold);Text("${estadoPantalla.jugadores.size} jugadores")}}}
  Spacer(Modifier.height(20.dp));Text("Puntuaciones",fontSize=19.sp,fontWeight=FontWeight.Bold)
  Spacer(Modifier.height(10.dp))
  ListaAplicacion(Modifier.weight(1f)){items(estadoPantalla.jugadores,key={it.id}){p->
    TarjetaBoardLog { Text(p.nombre,fontWeight=FontWeight.Bold);Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly,verticalAlignment=Alignment.CenterVertically) {IconButton(onClick={modeloVista.alEvento(EventoPartidaEnCurso.CambiarPuntos(p.id,-1))},enabled=m.estadoPartida==ESTADO_EN_CURSO) {Icon(Icons.Default.Remove,"Restar")};Text("${estadoPantalla.puntuaciones[p.id]?:0}",fontWeight=FontWeight.Bold,fontSize=20.sp);IconButton(onClick={modeloVista.alEvento(EventoPartidaEnCurso.CambiarPuntos(p.id,1))},enabled=m.estadoPartida==ESTADO_EN_CURSO){Icon(Icons.Default.Add,"Sumar")}} }
  }}
  if(m.estadoPartida==ESTADO_FINALIZADO) BotonPrincipal("Ver resultados"){modeloVista.alEvento(EventoPartidaEnCurso.Resultados)} else {
    BotonPrincipal("Guardar y salir"){modeloVista.alEvento(EventoPartidaEnCurso.Guardar)}
    Spacer(Modifier.height(8.dp));BotonPrincipal("Finalizar partida",esPeligroso=true,enabled=!estadoPantalla.ocupado){modeloVista.alEvento(EventoPartidaEnCurso.Finalizar)}
  }
 }
}
