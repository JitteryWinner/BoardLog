package com.example.boardlog.funcionalidades.perfiljugador

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoPerfilJugador { data class Abrir(val destino:Destino):EventoPerfilJugador }
