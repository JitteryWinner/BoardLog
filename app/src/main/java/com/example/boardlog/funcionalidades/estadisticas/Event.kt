package com.example.boardlog.funcionalidades.estadisticas

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoEstadisticas {data class Filtrar(val valor:FiltroFecha):EventoEstadisticas;data class Abrir(val destino:Destino):EventoEstadisticas}
