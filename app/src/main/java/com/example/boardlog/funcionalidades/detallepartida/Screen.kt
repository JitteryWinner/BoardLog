package com.example.boardlog.funcionalidades.detallepartida

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

@Composable fun PantallaDetallePartida(repositorio:RepositorioBoardLog,idPartida:String,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:DetallePartidaViewModel=viewModel(factory=FabricaViewModel{DetallePartidaViewModel(repositorio,idPartida)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Detalle de partida",back=true,alVolver=alVolver,alNavegar={modeloVista.alEvento(EventoDetallePartida.Abrir(it))}) {
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
 val m=estadoPantalla.partida;if(m==null){Text("Cargando partida...");return@Column}
 val g=CatalogoJuegos.obtener(m.idJuego)
 if(g!=null) PortadaJuego(g,Modifier.fillMaxWidth().height(190.dp))
 Encabezado("${g?.nombre?:"Juego"} - Partida")
 Text("Posiciones",fontWeight=FontWeight.Bold)
 val clasificados=estadoPantalla.clasificacion
 clasificados.forEachIndexed{idx,entry->TarjetaBoardLog{Row {Text("${idx+1}.",Modifier.width(35.dp),fontWeight=FontWeight.Bold);Text(entry.nombre,Modifier.weight(1f));Text("${entry.puntos} pts")}};Spacer(Modifier.height(7.dp))}
 Spacer(Modifier.height(14.dp));Text("Ganador: ${estadoPantalla.nombreGanador}",fontWeight=FontWeight.Bold,color=VerdeBoardLog)
 }
 }
}
