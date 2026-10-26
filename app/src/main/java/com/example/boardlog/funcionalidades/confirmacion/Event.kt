package com.example.boardlog.funcionalidades.confirmacion

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoConfirmacion {data object Iniciar:EventoConfirmacion;data object Cancelar:EventoConfirmacion;data object EditarJuego:EventoConfirmacion;data object EditarJugadores:EventoConfirmacion}
