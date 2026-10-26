package com.example.boardlog.funcionalidades.inicio

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoInicio(val nombreUsuario: String = "Usuario", val partidas: List<EntidadPartida> = emptyList(), val jugadores: List<EntidadJugador> = emptyList(), val porcentajeVictorias:Int=0, val cantidadFinalizadas:Int=0)
