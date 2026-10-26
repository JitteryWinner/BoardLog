package com.example.boardlog.funcionalidades.resultados

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

@Composable fun PantallaResultados(repositorio:RepositorioBoardLog,idPartida:String,irA:(Destino)->Unit) {
 val modeloVista:ResultadosViewModel=viewModel(factory=FabricaViewModel{ResultadosViewModel(repositorio,idPartida)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Partida Finalizada - Resultados",alNavegar={irA(it)}) {
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
 val m=estadoPantalla.partida;if(m==null){Text("Cargando resultados...");return@Column}
 val clasificados=estadoPantalla.clasificacion;val ganador=estadoPantalla.nombreGanador
 Spacer(Modifier.height(12.dp));Column(Modifier.fillMaxWidth(),horizontalAlignment=Alignment.CenterHorizontally){Text("🏆",fontSize=55.sp);Text("${ganador.uppercase()} GANÓ",fontSize=25.sp,fontWeight=FontWeight.Bold,color=VerdeBoardLog)}
 Spacer(Modifier.height(18.dp))
 clasificados.forEachIndexed{index,entry->TarjetaBoardLog{Row(verticalAlignment=Alignment.CenterVertically){Text("${index+1}.",fontWeight=FontWeight.Bold);Spacer(Modifier.width(8.dp));Text(entry.nombre,Modifier.weight(1f));Text("${entry.puntos} pts",fontWeight=FontWeight.Bold)}};Spacer(Modifier.height(7.dp))}
 Spacer(Modifier.height(18.dp));BotonPrincipal("Ver detalle de partida"){modeloVista.alEvento(EventoResultados.Detalle)}
 Spacer(Modifier.height(8.dp));BotonContorno("Volver al inicio"){modeloVista.alEvento(EventoResultados.Inicio)}
 }
 }
}
