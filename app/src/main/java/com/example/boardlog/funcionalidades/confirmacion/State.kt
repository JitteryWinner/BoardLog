package com.example.boardlog.funcionalidades.confirmacion

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.JuegoMesa

data class EstadoConfirmacion(
    val juego: JuegoMesa? = null,
    val jugadores: List<EntidadJugador> = emptyList(),
    val ocupado: Boolean = false,
    val error: String? = null
)
