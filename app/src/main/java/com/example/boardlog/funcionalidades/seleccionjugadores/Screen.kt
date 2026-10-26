package com.example.boardlog.funcionalidades.seleccionjugadores

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

@Composable fun PantallaSeleccionJugadores(repositorio:RepositorioBoardLog,idJuego:String,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:SeleccionJugadoresViewModel=viewModel(factory=FabricaViewModel{SeleccionJugadoresViewModel(repositorio,idJuego)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Nueva partida",back=true,alVolver=alVolver,alNavegar={modeloVista.alEvento(EventoSeleccionJugadores.Abrir(it))}) {
   BarraBusqueda(estadoPantalla.busqueda,"Buscar jugador",{modeloVista.alEvento(EventoSeleccionJugadores.Buscar(it))})
   Text("Jugadores",fontWeight=FontWeight.Bold,fontSize=19.sp)
   Spacer(Modifier.height(10.dp))
   // El espacio flexible mantiene visibles los botones incluso con muchos jugadores.
   ListaAplicacion(Modifier.weight(1f)){items(estadoPantalla.jugadoresVisibles,key={it.id}){p->
      TarjetaBoardLog(onClick={modeloVista.alEvento(EventoSeleccionJugadores.Alternar(p.id))}){Row(verticalAlignment=Alignment.CenterVertically){Icon(Icons.Default.AccountCircle,null,Modifier.size(38.dp),tint=NaranjaBoardLog);Spacer(Modifier.width(10.dp));Text(p.nombre,Modifier.weight(1f));Checkbox(checked=p.id in estadoPantalla.seleccionados,onCheckedChange={modeloVista.alEvento(EventoSeleccionJugadores.Alternar(p.id))})}}
   }}
   Text("${estadoPantalla.seleccionados.size} jugadores seleccionados",fontWeight=FontWeight.Bold)
   Text("${estadoPantalla.juego?.nombre?:"Juego"} requiere entre ${estadoPantalla.juego?.minimoJugadores?:0} y ${estadoPantalla.juego?.maximoJugadores?:0} jugadores.",fontSize=12.sp)
   estadoPantalla.error?.let{Text(it,color=RojoBoardLog)}
   Spacer(Modifier.height(10.dp));BotonPrincipal("Siguiente",enabled=estadoPantalla.puedeContinuar){modeloVista.alEvento(EventoSeleccionJugadores.Siguiente)}
 }
}
