package com.example.boardlog.funcionalidades.confirmacion

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoConfirmacion(val juego:JuegoMesa?=null,val jugadores:List<EntidadJugador> = emptyList(), val ocupado:Boolean=false, val error:String?=null)
