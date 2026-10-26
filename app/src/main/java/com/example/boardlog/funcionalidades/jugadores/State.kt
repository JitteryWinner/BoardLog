package com.example.boardlog.funcionalidades.jugadores

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoJugadores(val busqueda:String="",val jugadores:List<EntidadJugador> = emptyList(),val jugadoresVisibles:List<EntidadJugador> = emptyList())
