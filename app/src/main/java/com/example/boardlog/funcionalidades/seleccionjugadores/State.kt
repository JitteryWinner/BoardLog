package com.example.boardlog.funcionalidades.seleccionjugadores

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.JuegoMesa

data class EstadoSeleccionJugadores(
    val juego: JuegoMesa? = null,
    val busqueda: String = "",
    val jugadores: List<EntidadJugador> = emptyList(),
    val jugadoresVisibles: List<EntidadJugador> = emptyList(),
    val puedeContinuar: Boolean = false,
    val seleccionados: Set<String> = emptySet(),
    val error: String? = null
)
