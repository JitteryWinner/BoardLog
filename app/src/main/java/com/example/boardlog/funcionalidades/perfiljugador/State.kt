package com.example.boardlog.funcionalidades.perfiljugador

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class JuegoPerfil(val juego:JuegoMesa,val cantidad:Int)

data class EstadoPerfilJugador(val jugador:EntidadJugador?=null,val partidas:List<EntidadPartida> = emptyList(),val victorias:Int=0,val porcentaje:Int=0,val completadas:List<EntidadPartida> = emptyList(),val juegosMasJugados:List<JuegoPerfil> = emptyList())
