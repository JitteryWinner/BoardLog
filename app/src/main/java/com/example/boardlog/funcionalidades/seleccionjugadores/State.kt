package com.example.boardlog.funcionalidades.seleccionjugadores

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoSeleccionJugadores(val juego:JuegoMesa?=null,val busqueda:String="",val jugadores:List<EntidadJugador> = emptyList(),val jugadoresVisibles:List<EntidadJugador> = emptyList(),val puedeContinuar:Boolean=false,val seleccionados:Set<String> = emptySet(),val error:String?=null)
