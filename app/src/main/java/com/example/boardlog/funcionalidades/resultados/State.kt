package com.example.boardlog.funcionalidades.resultados

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida

data class LineaPuntuacion(val nombre: String, val idJugador: String, val puntos: Int)

data class EstadoResultados(
    val partida: EntidadPartida? = null,
    val jugadores: List<EntidadJugador> = emptyList(),
    val clasificacion: List<LineaPuntuacion> = emptyList(),
    val nombreGanador: String = "Sin ganador"
)
