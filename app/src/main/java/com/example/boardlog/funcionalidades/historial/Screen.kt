package com.example.boardlog.funcionalidades.historial

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

@Composable fun PantallaHistorial(repositorio:RepositorioBoardLog,irA:(Destino)->Unit) {
 val modeloVista:HistorialViewModel=viewModel(factory=FabricaViewModel{HistorialViewModel(repositorio)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(alNavegar={modeloVista.alEvento(EventoHistorial.Abrir(it))}) {
 Encabezado("Historial")
 BarraBusqueda(estadoPantalla.busqueda,"Buscar partida o juego",{modeloVista.alEvento(EventoHistorial.Buscar(it))})
 ListaAplicacion{ val filtradas=estadoPantalla.partidasVisibles
    if(filtradas.isEmpty()) item{Text("No se encontraron partidas finalizadas.")}
    items(filtradas,key={it.id}) { m-> val g=CatalogoJuegos.obtener(m.idJuego)
      TarjetaBoardLog(onClick={modeloVista.alEvento(EventoHistorial.Abrir(Destino.DetallePartida(m.id)))}){Row(verticalAlignment=Alignment.CenterVertically){if(g!=null)PortadaJuego(g,Modifier.size(84.dp));Spacer(Modifier.width(12.dp));Column{Text(g?.nombre?:"Juego",fontWeight=FontWeight.Bold);Text("${m.ids().size} jugadores");Text("🏆 ${estadoPantalla.jugadores.find{it.id==m.idGanador()}?.nombre?:"Sin ganador"} ganó",fontSize=12.sp,color=VerdeBoardLog);Text("Ver detalle",color=NaranjaBoardLog,fontSize=12.sp)}}}
    }
 }
 }
}
