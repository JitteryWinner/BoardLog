package com.example.boardlog.funcionalidades.partidaencurso

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoPartidaEnCurso(val partida:EntidadPartida?=null,val juego:JuegoMesa?=null,val jugadores:List<EntidadJugador> = emptyList(),val puntuaciones:Map<String,Int> = emptyMap(),val ocupado:Boolean=false)
