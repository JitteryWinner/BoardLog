package com.example.boardlog.funcionalidades.historial

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class HistorialViewModel(repositorio:RepositorioBoardLog):ModeloVistaMvi<EstadoHistorial,EventoHistorial>(EstadoHistorial()) {
 init{viewModelScope.launch {combine(repositorio.partidas,repositorio.jugadores){partidas,jugadores->partidas to jugadores}.collect{(partidas,jugadores)->reducir{it.copy(partidas=partidas,jugadores=jugadores,partidasVisibles=filtered(partidas,it.busqueda))}}}}
 private fun filtered(partidas:List<EntidadPartida>,busqueda:String):List<EntidadPartida> = partidas.filter {
    it.estadoPartida==ESTADO_FINALIZADO && (CatalogoJuegos.obtener(it.idJuego)?.nombre?.contains(busqueda,ignoreCase=true)==true)
 }
 override fun alEvento(event:EventoHistorial){when(event){is EventoHistorial.Buscar->reducir{it.copy(busqueda=event.valor,partidasVisibles=filtered(it.partidas,event.valor))};is EventoHistorial.Abrir->navegar(event.destino)}}
}
