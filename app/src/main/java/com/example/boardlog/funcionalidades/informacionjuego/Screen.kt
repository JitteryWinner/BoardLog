package com.example.boardlog.funcionalidades.informacionjuego

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

@Composable fun PantallaInformacionJuego(repositorio:RepositorioBoardLog,idJuego:String,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:InformacionJuegoViewModel=viewModel(factory=FabricaViewModel { InformacionJuegoViewModel(repositorio,idJuego) });val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()
 LaunchedEffect(modeloVista){modeloVista.navegacion.collect { irA(it) }}
 PaginaBoardLog(title="Información del juego",back=true,alVolver=alVolver,alNavegar={modeloVista.alEvento(EventoInformacionJuego.Abrir(it))}) {
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
   val g=estadoPantalla.juego
   if(g==null) { Text("Juego no encontrado");return@Column }
   PortadaJuego(g,Modifier.fillMaxWidth().height(200.dp))
   Encabezado(g.nombre)
   Text("${g.minimoJugadores}-${g.maximoJugadores} jugadores · Estrategia y diversión",color=VerdeBoardLog)
   Spacer(Modifier.height(12.dp));Text(g.descripcion)
   Spacer(Modifier.height(10.dp))
   if(estadoPantalla.cargando) Text("Buscando información adicional en internet...",fontSize=12.sp)
   else Text(estadoPantalla.informacionExterna?.let { "Referencia de Wikidata: $it" } ?: "Sin referencia externa; ficha local disponible.",fontSize=12.sp,color=Color.Gray)
   Spacer(Modifier.height(20.dp)); Text("Tus estadísticas",fontWeight=FontWeight.Bold)
   Text("${estadoPantalla.partidas.size} partidas registradas")
   Spacer(Modifier.height(14.dp));Text("Partidas recientes",fontWeight=FontWeight.Bold)
   estadoPantalla.partidas.take(3).forEach { m -> Text("• ${m.ids().size} jugadores · ${if(m.estadoPartida==ESTADO_FINALIZADO) "Finalizada" else "En progreso"}", modifier=Modifier.clickable { modeloVista.alEvento(EventoInformacionJuego.Abrir(if(m.estadoPartida==ESTADO_FINALIZADO)Destino.DetallePartida(m.id) else Destino.PartidaEnCurso(m.id))) }.padding(6.dp)) }
   Spacer(Modifier.height(16.dp)); BotonPrincipal("Iniciar partida") { modeloVista.alEvento(EventoInformacionJuego.Iniciar) }
   Spacer(Modifier.height(8.dp)); BotonContorno("Ver historial") { modeloVista.alEvento(EventoInformacionJuego.Abrir(Destino.Historial)) }
 }
 }
}
