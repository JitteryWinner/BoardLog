package com.example.boardlog.funcionalidades.seleccionjugadores

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class SeleccionJugadoresViewModel(private val repositorio:RepositorioBoardLog, private val idJuego:String):ModeloVistaMvi<EstadoSeleccionJugadores,EventoSeleccionJugadores>(EstadoSeleccionJugadores(juego=CatalogoJuegos.obtener(idJuego))) {
 init {viewModelScope.launch {combine(repositorio.jugadores,repositorio.idsJugadoresPendientes){jugadores,selected->jugadores to selected}.collect{(jugadores,selected)->reducir{it.copy(jugadores=jugadores,jugadoresVisibles=jugadores.filter {p->p.nombre.contains(it.busqueda,true)},puedeContinuar=selected.size in (it.juego?.minimoJugadores?:1)..(it.juego?.maximoJugadores?:0),seleccionados=selected)}}}}
 override fun alEvento(event:EventoSeleccionJugadores){when(event){
 is EventoSeleccionJugadores.Buscar->reducir{it.copy(busqueda=event.valor,jugadoresVisibles=it.jugadores.filter {p->p.nombre.contains(event.valor,true)})}
 is EventoSeleccionJugadores.Alternar->{repositorio.alternarJugadorPendiente(event.id);reducir{it.copy(error=null)}}
 EventoSeleccionJugadores.Siguiente->{val g=estado.value.juego;if(g!=null && estado.value.seleccionados.size in g.minimoJugadores..g.maximoJugadores)navegar(Destino.ConfirmacionPartida(idJuego)) else reducir{it.copy(error="Selecciona de ${g?.minimoJugadores?:0} a ${g?.maximoJugadores?:0} jugadores.")}}
 is EventoSeleccionJugadores.Abrir->navegar(event.destino)
 } }
}
