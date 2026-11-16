package com.example.boardlog.funcionalidades.partidaencurso

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.JuegoMesa

data class EstadoPartidaEnCurso(
    val partida: EntidadPartida? = null,
    val juego: JuegoMesa? = null,
    val jugadores: List<EntidadJugador> = emptyList(),
    val puntuaciones: Map<String, Int> = emptyMap(),
    val ocupado: Boolean = false
)
