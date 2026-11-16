package com.example.boardlog.funcionalidades.historial

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida

data class EstadoHistorial(
    val busqueda: String = "",
    val partidas: List<EntidadPartida> = emptyList(),
    val partidasVisibles: List<EntidadPartida> = emptyList(),
    val jugadores: List<EntidadJugador> = emptyList()
)
