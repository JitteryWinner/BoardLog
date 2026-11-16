package com.example.boardlog.funcionalidades.informacionjuego

import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.JuegoMesa

data class EstadoInformacionJuego(
    val juego: JuegoMesa? = null,
    val informacionExterna: String? = null,
    val cargando: Boolean = true,
    val partidas: List<EntidadPartida> = emptyList()
)
