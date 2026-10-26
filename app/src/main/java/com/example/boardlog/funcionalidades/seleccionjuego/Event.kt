package com.example.boardlog.funcionalidades.seleccionjuego

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoSeleccionJuego { data class Buscar(val valor:String):EventoSeleccionJuego; data class Elegir(val idJuego:String):EventoSeleccionJuego; data class Abrir(val destino:Destino):EventoSeleccionJuego }
