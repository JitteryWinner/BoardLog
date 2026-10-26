package com.example.boardlog.funcionalidades.estadisticas

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

@Composable fun PantallaEstadisticas(repositorio:RepositorioBoardLog,irA:(Destino)->Unit) {
 val modeloVista:EstadisticasViewModel=viewModel(factory=FabricaViewModel{EstadisticasViewModel(repositorio)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(alNavegar={modeloVista.alEvento(EventoEstadisticas.Abrir(it))}) {
 Encabezado("Mis estadísticas")
 Row(horizontalArrangement=Arrangement.spacedBy(7.dp)) {FiltroFecha.entries.forEach{f->FilterChip(selected=estadoPantalla.filtro==f,onClick={modeloVista.alEvento(EventoEstadisticas.Filtrar(f))},label={Text(when(f){FiltroFecha.MES->"Este mes";FiltroFecha.ANIO->"Este año";FiltroFecha.TODOS->"Todo"})})}}
 Spacer(Modifier.height(18.dp));Text("Resumen general (jugador de ejemplo: David)",fontWeight=FontWeight.Bold)
 Spacer(Modifier.height(10.dp));TarjetaBoardLog {Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceEvenly){MetricaEstadistica("${estadoPantalla.jugadas}","Partidas jugadas");MetricaEstadistica("${estadoPantalla.porcentajeVictorias}%","Victorias")}}
 Spacer(Modifier.height(18.dp));Text("Mis mejores juegos",fontWeight=FontWeight.Bold)
 ListaAplicacion{if(estadoPantalla.juegosMasJugados.isEmpty())item{Text("Todavía no hay partidas en este período.")}
   items(estadoPantalla.juegosMasJugados){entry->TarjetaBoardLog{Row(verticalAlignment=Alignment.CenterVertically){PortadaJuego(entry.juego,Modifier.size(56.dp));Spacer(Modifier.width(10.dp));Column{Text(entry.juego.nombre,fontWeight=FontWeight.Bold);Text("${entry.partidas} partidas · ${entry.victorias} victorias")}}}}
 }
 }
}
@Composable private fun MetricaEstadistica(value:String,title:String){Column(horizontalAlignment=Alignment.CenterHorizontally){Text(value,fontSize=26.sp,fontWeight=FontWeight.Bold,color=VerdeBoardLog);Text(title,fontSize=12.sp)}}
