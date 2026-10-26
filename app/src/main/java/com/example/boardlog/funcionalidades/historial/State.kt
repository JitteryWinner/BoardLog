package com.example.boardlog.funcionalidades.historial

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoHistorial(val busqueda:String="",val partidas:List<EntidadPartida> = emptyList(),val partidasVisibles:List<EntidadPartida> = emptyList(),val jugadores:List<EntidadJugador> = emptyList())
