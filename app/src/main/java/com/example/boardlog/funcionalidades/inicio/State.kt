package com.example.boardlog.funcionalidades.inicio

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida

data class EstadoInicio(
    val nombreUsuario: String = "Usuario",
    val partidas: List<EntidadPartida> = emptyList(),
    val jugadores: List<EntidadJugador> = emptyList(),
    val porcentajeVictorias: Int = 0,
    val cantidadFinalizadas: Int = 0
)
