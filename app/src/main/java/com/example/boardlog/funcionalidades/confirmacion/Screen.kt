package com.example.boardlog.funcionalidades.confirmacion

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

@Composable fun PantallaConfirmacion(repositorio:RepositorioBoardLog,idJuego:String,irA:(Destino)->Unit,alVolver:()->Unit) {
 val modeloVista:ConfirmacionViewModel=viewModel(factory=FabricaViewModel{ConfirmacionViewModel(repositorio,idJuego)});val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle();LaunchedEffect(modeloVista){modeloVista.navegacion.collect{irA(it)}}
 PaginaBoardLog(title="Nueva partida",back=true,barraInferior=false,alVolver=alVolver) {
    val g=estadoPantalla.juego
    Spacer(Modifier.height(12.dp))
    if(g!=null) TarjetaBoardLog{Row(verticalAlignment=Alignment.CenterVertically){PortadaJuego(g,Modifier.size(110.dp));Spacer(Modifier.width(12.dp));Column(Modifier.weight(1f)){Text(g.nombre,fontWeight=FontWeight.Bold);Text("${g.minimoJugadores}-${g.maximoJugadores} jugadores");TextButton(onClick={modeloVista.alEvento(EventoConfirmacion.EditarJuego)}){Text("Editar")}}}}
    Spacer(Modifier.height(16.dp));Text("Jugadores",fontSize=19.sp,fontWeight=FontWeight.Bold)
    Column(Modifier.weight(1f).verticalScroll(rememberScrollState())){estadoPantalla.jugadores.forEach {p->Spacer(Modifier.height(8.dp));TarjetaBoardLog{Row(verticalAlignment=Alignment.CenterVertically){Icon(Icons.Default.AccountCircle,null,tint=NaranjaBoardLog);Spacer(Modifier.width(10.dp));Text(p.nombre)}}};TextButton(onClick={modeloVista.alEvento(EventoConfirmacion.EditarJugadores)}){Text("Editar jugadores")}}
    estadoPantalla.error?.let{Text(it,color=RojoBoardLog)}
    BotonPrincipal("Iniciar partida",enabled=!estadoPantalla.ocupado){modeloVista.alEvento(EventoConfirmacion.Iniciar)}
    Spacer(Modifier.height(8.dp));BotonPrincipal("Cancelar",esPeligroso=true){modeloVista.alEvento(EventoConfirmacion.Cancelar)}
 }
}
