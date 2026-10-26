package com.example.boardlog.funcionalidades.resultados

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoResultados {data object Inicio:EventoResultados;data object Detalle:EventoResultados}
