package com.example.boardlog.funcionalidades.juegos

import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.JuegoMesa

data class EstadoJuegos(
    val busqueda: String = "",
    val soloFavoritos: Boolean = false,
    val juegos: List<JuegoMesa> = CatalogoJuegos.juegos,
    val juegosVisibles: List<JuegoMesa> = CatalogoJuegos.juegos
)
