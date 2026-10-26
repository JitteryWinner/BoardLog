package com.example.boardlog.funcionalidades.jugadores

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class JugadoresViewModel(repositorio:RepositorioBoardLog):ModeloVistaMvi<EstadoJugadores,EventoJugadores>(EstadoJugadores()) {
 init { viewModelScope.launch { repositorio.jugadores.collect { jugadores -> reducir{ it.copy(jugadores=jugadores,jugadoresVisibles=jugadores.filter {p -> p.nombre.contains(it.busqueda,true)}) } } } }
 override fun alEvento(event:EventoJugadores){when(event){is EventoJugadores.Buscar -> reducir{it.copy(busqueda=event.valor,jugadoresVisibles=it.jugadores.filter {p -> p.nombre.contains(event.valor,true)})};is EventoJugadores.Abrir -> navegar(event.destino)}}
}
