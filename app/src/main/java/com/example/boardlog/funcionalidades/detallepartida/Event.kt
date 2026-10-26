package com.example.boardlog.funcionalidades.detallepartida

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoDetallePartida {data class Abrir(val destino:Destino):EventoDetallePartida}
