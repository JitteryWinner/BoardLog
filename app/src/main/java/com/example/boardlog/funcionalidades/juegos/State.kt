package com.example.boardlog.funcionalidades.juegos

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

data class EstadoJuegos(val busqueda: String="", val soloFavoritos:Boolean=false, val juegos:List<JuegoMesa> = CatalogoJuegos.juegos,val juegosVisibles:List<JuegoMesa> = CatalogoJuegos.juegos)
