package com.example.boardlog.funcionalidades.detallepartida

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida

data class PuntuacionDetalle(val nombre: String, val idJugador: String, val puntos: Int)

data class EstadoDetallePartida(
    val partida: EntidadPartida? = null,
    val jugadores: List<EntidadJugador> = emptyList(),
    val clasificacion: List<PuntuacionDetalle> = emptyList(),
    val nombreGanador: String = "Sin ganador"
)
