package com.example.boardlog.funcionalidades.presentacion

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoPresentacion { data object Listo : EventoPresentacion }
