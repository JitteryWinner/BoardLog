package com.example.boardlog.funcionalidades.inicio

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class InicioViewModel(private val repositorio: RepositorioBoardLog) : ModeloVistaMvi<EstadoInicio, EventoInicio>(EstadoInicio()) {
    init { viewModelScope.launch { combine(repositorio.partidas,repositorio.jugadores) { partidas,jugadores -> partidas to jugadores }.collect { (partidas,jugadores) -> reducir{ it.copy(partidas=partidas,jugadores=jugadores,cantidadFinalizadas=partidas.count{m->m.estadoPartida==ESTADO_FINALIZADO},porcentajeVictorias=partidas.filter{m->m.estadoPartida==ESTADO_FINALIZADO}.let{finalizadas->if(finalizadas.isEmpty())0 else finalizadas.count{m->m.idGanador()=="p1"}*100/finalizadas.size}) } } } }
    override fun alEvento(event: EventoInicio) { when(event) { is EventoInicio.Abrir -> navegar(event.destino) } }
}
