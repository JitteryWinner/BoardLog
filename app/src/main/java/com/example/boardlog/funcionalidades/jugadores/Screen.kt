package com.example.boardlog.funcionalidades.jugadores

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

@Composable fun PantallaJugadores(repositorio:RepositorioBoardLog,irA:(Destino)->Unit) {
 val modeloVista:JugadoresViewModel=viewModel(factory=FabricaViewModel{JugadoresViewModel(repositorio)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()
 LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(alNavegar={modeloVista.alEvento(EventoJugadores.Abrir(it))}) {
  Encabezado("Jugadores")
  Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) { Button(onClick={modeloVista.alEvento(EventoJugadores.Abrir(Destino.Juegos))},colors=ButtonDefaults.buttonColors(containerColor=NaranjaBoardLog)) {Text("Juegos")}; OutlinedButton(onClick={}) {Text("Jugadores")} }
  BarraBusqueda(estadoPantalla.busqueda,"Buscar jugador",{modeloVista.alEvento(EventoJugadores.Buscar(it))})
  ListaAplicacion { val visibles=estadoPantalla.jugadoresVisibles
      if(visibles.isEmpty()) item{Text("No hay jugadores disponibles.")}
      items(visibles,key={it.id}){p->TarjetaBoardLog(onClick={modeloVista.alEvento(EventoJugadores.Abrir(Destino.PerfilJugador(p.id)))}){Row(verticalAlignment=Alignment.CenterVertically) { Icon(Icons.Default.AccountCircle,null,modifier=Modifier.size(44.dp),tint=NaranjaBoardLog);Spacer(Modifier.width(10.dp));Text(p.nombre,modifier=Modifier.weight(1f),fontWeight=FontWeight.Bold);Icon(Icons.Default.ArrowForward,null) }}}
  }
 }
}
