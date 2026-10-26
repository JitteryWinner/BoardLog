package com.example.boardlog.funcionalidades.juegos

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

@Composable fun PantallaJuegos(irA:(Destino)->Unit) {
 val modeloVista:JuegosViewModel=viewModel();val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()
 LaunchedEffect(modeloVista){modeloVista.navegacion.collect { irA(it) }}
 PaginaBoardLog(alNavegar={modeloVista.alEvento(EventoJuegos.Abrir(it))}) {
   Encabezado("Juegos")
   Row(horizontalArrangement=Arrangement.spacedBy(9.dp)) {
     Button(onClick={}, colors=ButtonDefaults.buttonColors(containerColor=NaranjaBoardLog), modifier=Modifier.weight(1f)) { Text("Juegos") }
     OutlinedButton(onClick={modeloVista.alEvento(EventoJuegos.Abrir(Destino.Jugadores))}, modifier=Modifier.weight(1f)) { Text("Jugadores") }
   }
   BarraBusqueda(estadoPantalla.busqueda,"Buscar juego",{modeloVista.alEvento(EventoJuegos.Buscar(it))})
   Row { FilterChip(selected=!estadoPantalla.soloFavoritos,onClick={if(estadoPantalla.soloFavoritos)modeloVista.alEvento(EventoJuegos.AlternarFavoritos)},label={Text("Todos")}); Spacer(Modifier.width(10.dp)); FilterChip(selected=estadoPantalla.soloFavoritos,onClick={modeloVista.alEvento(EventoJuegos.AlternarFavoritos)},label={Text("Favoritos")}) }
   ListaAplicacion { val visibles=estadoPantalla.juegosVisibles
      if(visibles.isEmpty()) item { Text("No encontramos juegos.") }
      items(visibles.chunked(2)) { chunk -> Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(12.dp)) { chunk.forEach { juego ->
        Card(onClick={modeloVista.alEvento(EventoJuegos.Abrir(Destino.InformacionJuego(juego.id)))}, modifier=Modifier.weight(1f),colors=CardDefaults.cardColors(containerColor=Color.White)) {
            Column(Modifier.padding(8.dp),horizontalAlignment=Alignment.CenterHorizontally) { PortadaJuego(juego,Modifier.height(130.dp).fillMaxWidth()); Spacer(Modifier.height(6.dp)); Text(juego.nombre,fontWeight=FontWeight.Bold);Text("${juego.minimoJugadores}-${juego.maximoJugadores} jugadores",fontSize=11.sp) }
        }
      }; if(chunk.size==1) Spacer(Modifier.weight(1f)) } }
   }
 }
}
