package com.example.boardlog.funcionalidades.perfiljugador

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class PerfilJugadorViewModel(repositorio:RepositorioBoardLog, private val idJugador:String):ModeloVistaMvi<EstadoPerfilJugador,EventoPerfilJugador>(EstadoPerfilJugador()) {
    init { viewModelScope.launch {
        combine(repositorio.jugadores,repositorio.partidas) {jugadores,partidas -> jugadores to partidas}.collect { (jugadores,partidas) ->
            val partidasJugador=partidas.filter {idJugador in it.ids()}
            val finalizadas=partidasJugador.filter {it.estadoPartida==ESTADO_FINALIZADO}
            val victorias=finalizadas.count {it.idGanador()==idJugador}
            val juegos=partidasJugador.groupingBy {it.idJuego}.eachCount().entries.sortedByDescending {it.value}.mapNotNull {(id,count)->CatalogoJuegos.obtener(id)?.let{JuegoPerfil(it,count)}}
            reducir{it.copy(jugador=jugadores.find {p->p.id==idJugador},partidas=partidasJugador,completadas=finalizadas,victorias=victorias,porcentaje=if(finalizadas.isEmpty())0 else victorias*100/finalizadas.size,juegosMasJugados=juegos)}
        }
    } }
    override fun alEvento(event:EventoPerfilJugador) {when(event){is EventoPerfilJugador.Abrir->navegar(event.destino)}}
}
