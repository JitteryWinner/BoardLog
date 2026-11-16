package com.example.boardlog.funcionalidades.jugadores

import com.example.boardlog.datos.EntidadJugador

data class EstadoJugadores(
    val busqueda: String = "",
    val jugadores: List<EntidadJugador> = emptyList(),
    val jugadoresVisibles: List<EntidadJugador> = emptyList()
)
