package com.example.boardlog.funcionalidades.partidaencurso

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class PartidaEnCursoViewModel(private val repositorio:RepositorioBoardLog,private val idPartida:String):ModeloVistaMvi<EstadoPartidaEnCurso,EventoPartidaEnCurso>(EstadoPartidaEnCurso()) {
 init {viewModelScope.launch {combine(repositorio.partidas,repositorio.jugadores){partidas,jugadores->partidas.find{it.id==idPartida} to jugadores}.collect{(partida,jugadores)->reducir{it.copy(partida=partida,juego=partida?.let{CatalogoJuegos.obtener(it.idJuego)},jugadores=jugadores.filter{p->p.id in (partida?.ids()?:emptyList())},puntuaciones=partida?.puntos()?:emptyMap())}}}}
 override fun alEvento(event:EventoPartidaEnCurso){when(event){
 is EventoPartidaEnCurso.CambiarPuntos->viewModelScope.launch{repositorio.cambiarPuntos(idPartida,event.idJugador,event.delta)}
 EventoPartidaEnCurso.Guardar->navegar(Destino.Inicio)
 EventoPartidaEnCurso.Resultados->navegar(Destino.Resultados(idPartida))
 EventoPartidaEnCurso.Finalizar->viewModelScope.launch{reducir{it.copy(ocupado=true)};repositorio.finalizarPartida(idPartida);navegar(Destino.Resultados(idPartida))}
 }}
}
