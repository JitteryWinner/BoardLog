package com.example.boardlog.funcionalidades.seleccionjuego

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

@Composable fun PantallaSeleccionJuego(repositorio:RepositorioBoardLog,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:SeleccionJuegoViewModel=viewModel(factory=FabricaViewModel{SeleccionJuegoViewModel(repositorio)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Nueva partida",back=true,alVolver=alVolver,alNavegar={modeloVista.alEvento(EventoSeleccionJuego.Abrir(it))}) {
   BarraBusqueda(estadoPantalla.busqueda,"Buscar juego",{modeloVista.alEvento(EventoSeleccionJuego.Buscar(it))})
   Encabezado("Recientes")
   Row(horizontalArrangement=Arrangement.spacedBy(12.dp)){estadoPantalla.juegos.take(3).forEach{g->Card(onClick={modeloVista.alEvento(EventoSeleccionJuego.Elegir(g.id))}){PortadaJuego(g,Modifier.size(85.dp))}}}
   Encabezado("Todos los juegos")
   ListaAplicacion {val visibles=estadoPantalla.juegosVisibles;if(visibles.isEmpty())item{Text("No encontramos ese juego.")}
      items(visibles){g->TarjetaBoardLog(onClick={modeloVista.alEvento(EventoSeleccionJuego.Elegir(g.id))}){Row(verticalAlignment=Alignment.CenterVertically){PortadaJuego(g,Modifier.size(55.dp));Spacer(Modifier.width(12.dp));Text(g.nombre,modifier=Modifier.weight(1f),fontWeight=FontWeight.Bold);Icon(Icons.Default.ArrowForward,null)}}}
   }
 }
}
