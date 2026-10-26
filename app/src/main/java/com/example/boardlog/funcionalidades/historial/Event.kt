package com.example.boardlog.funcionalidades.historial

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoHistorial {data class Buscar(val valor:String):EventoHistorial;data class Abrir(val destino:Destino):EventoHistorial}
