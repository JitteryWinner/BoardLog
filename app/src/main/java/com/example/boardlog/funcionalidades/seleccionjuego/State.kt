package com.example.boardlog.funcionalidades.seleccionjuego

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoSeleccionJuego(val busqueda:String="",val juegos:List<JuegoMesa> = CatalogoJuegos.juegos,val juegosVisibles:List<JuegoMesa> = CatalogoJuegos.juegos)
