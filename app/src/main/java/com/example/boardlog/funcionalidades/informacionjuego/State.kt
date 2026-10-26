package com.example.boardlog.funcionalidades.informacionjuego

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoInformacionJuego(val juego:JuegoMesa?=null,val informacionExterna:String?=null,val cargando:Boolean=true, val partidas:List<EntidadPartida> = emptyList())
