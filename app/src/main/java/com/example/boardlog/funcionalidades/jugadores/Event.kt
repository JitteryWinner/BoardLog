package com.example.boardlog.funcionalidades.jugadores

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoJugadores { data class Buscar(val valor:String):EventoJugadores; data class Abrir(val destino:Destino):EventoJugadores }
