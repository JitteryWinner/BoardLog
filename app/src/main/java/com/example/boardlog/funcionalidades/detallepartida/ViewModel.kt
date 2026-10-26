package com.example.boardlog.funcionalidades.detallepartida

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class DetallePartidaViewModel(repositorio: RepositorioBoardLog, private val idPartida: String) : ModeloVistaMvi<EstadoDetallePartida, EventoDetallePartida>(EstadoDetallePartida()) {
    init { viewModelScope.launch {
        combine(repositorio.partidas,repositorio.jugadores) { partidas,jugadores -> partidas.find { it.id==idPartida } to jugadores }
            .collect { (partida,jugadores) -> reducir{ it.copy(partida=partida,jugadores=jugadores,clasificacion=partida?.puntos()?.toList()?.sortedByDescending{it.second}?.map{(id,puntos)->PuntuacionDetalle(jugadores.find{it.id==id}?.nombre?:"Jugador",id,puntos)}?:emptyList(),nombreGanador=jugadores.find{p->p.id==partida?.idGanador()}?.nombre?:"Sin ganador") } }
    } }
    override fun alEvento(event: EventoDetallePartida) { when(event) {
        is EventoDetallePartida.Abrir -> navegar(event.destino)
    } }
}
