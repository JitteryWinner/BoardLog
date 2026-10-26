package com.example.boardlog.funcionalidades.presentacion

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoPresentacion(val completo: Boolean = false)
