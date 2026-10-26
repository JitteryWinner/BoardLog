package com.example.boardlog.funcionalidades.resultados

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class ResultadosViewModel(repositorio:RepositorioBoardLog, private val idPartida:String):ModeloVistaMvi<EstadoResultados,EventoResultados>(EstadoResultados()){
 init{viewModelScope.launch{combine(repositorio.partidas,repositorio.jugadores){partidas,jugadores->partidas.find{it.id==idPartida} to jugadores}.collect{(partida,jugadores)->reducir{it.copy(partida=partida,jugadores=jugadores,clasificacion=partida?.puntos()?.toList()?.sortedByDescending{it.second}?.map { (id,puntos)->LineaPuntuacion(jugadores.find{it.id==id}?.nombre?:"Jugador",id,puntos) }?:emptyList(),nombreGanador=jugadores.find {p->p.id==partida?.idGanador()}?.nombre?:"Sin ganador")}}}}
 override fun alEvento(event:EventoResultados){when(event){EventoResultados.Inicio->navegar(Destino.Inicio);EventoResultados.Detalle->navegar(Destino.DetallePartida(idPartida))}}
}
