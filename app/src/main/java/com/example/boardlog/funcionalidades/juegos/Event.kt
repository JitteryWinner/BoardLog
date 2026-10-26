package com.example.boardlog.funcionalidades.juegos

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoJuegos { data class Buscar(val valor:String):EventoJuegos; data object AlternarFavoritos:EventoJuegos; data class Abrir(val destino:Destino):EventoJuegos }
