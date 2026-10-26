package com.example.boardlog.funcionalidades.estadisticas

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

enum class FiltroFecha{MES,ANIO,TODOS}
data class EstadisticaJuego(val juego:JuegoMesa,val partidas:Int,val victorias:Int)

data class EstadoEstadisticas(val filtro:FiltroFecha=FiltroFecha.TODOS,val partidas:List<EntidadPartida> = emptyList(),val partidasFiltradas:List<EntidadPartida> = emptyList(),val jugadores:List<EntidadJugador> = emptyList(),val jugadas:Int=0,val porcentajeVictorias:Int=0,val juegosMasJugados:List<EstadisticaJuego> = emptyList())
