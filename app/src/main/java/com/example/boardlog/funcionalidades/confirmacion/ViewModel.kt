package com.example.boardlog.funcionalidades.confirmacion

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class ConfirmacionViewModel(private val repositorio:RepositorioBoardLog,private val idJuego:String):ModeloVistaMvi<EstadoConfirmacion,EventoConfirmacion>(EstadoConfirmacion(juego=CatalogoJuegos.obtener(idJuego))) {
 init {viewModelScope.launch {combine(repositorio.jugadores,repositorio.idsJugadoresPendientes){jugadores,selected->jugadores.filter{it.id in selected}}.collect{jugadores->reducir{it.copy(jugadores=jugadores)}}}}
 override fun alEvento(event:EventoConfirmacion){when(event){
 EventoConfirmacion.Iniciar->viewModelScope.launch {reducir{it.copy(ocupado=true)};val id=repositorio.crearPartida(idJuego);if(id!=null)navegar(Destino.PartidaEnCurso(id)) else reducir{it.copy(ocupado=false,error="Revisa los jugadores seleccionados.")}}
 EventoConfirmacion.Cancelar->{repositorio.reiniciarSeleccion();navegar(Destino.Inicio)}
 EventoConfirmacion.EditarJuego->navegar(Destino.SeleccionJuego)
 EventoConfirmacion.EditarJugadores->navegar(Destino.SeleccionJugadores(idJuego))
 }}
}
