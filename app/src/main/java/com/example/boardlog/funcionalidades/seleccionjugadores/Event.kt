package com.example.boardlog.funcionalidades.seleccionjugadores

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoSeleccionJugadores {data class Buscar(val valor:String):EventoSeleccionJugadores;data class Alternar(val id:String):EventoSeleccionJugadores;data object Siguiente:EventoSeleccionJugadores;data class Abrir(val destino:Destino):EventoSeleccionJugadores}
