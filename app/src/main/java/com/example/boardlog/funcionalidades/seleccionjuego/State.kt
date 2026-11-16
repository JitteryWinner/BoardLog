package com.example.boardlog.funcionalidades.seleccionjuego

import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.JuegoMesa

data class EstadoSeleccionJuego(
    val busqueda: String = "",
    val juegos: List<JuegoMesa> = CatalogoJuegos.juegos,
    val juegosVisibles: List<JuegoMesa> = CatalogoJuegos.juegos
)
