package com.example.boardlog.funcionalidades.informacionjuego

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoInformacionJuego { data object Cargar:EventoInformacionJuego; data object Iniciar:EventoInformacionJuego; data class Abrir(val destino:Destino):EventoInformacionJuego }
