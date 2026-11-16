package com.example.boardlog.funcionalidades.perfiljugador

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.JuegoMesa

data class JuegoPerfil(val juego: JuegoMesa, val cantidad: Int)

data class EstadoPerfilJugador(
    val jugador: EntidadJugador? = null,
    val partidas: List<EntidadPartida> = emptyList(),
    val victorias: Int = 0,
    val porcentaje: Int = 0,
    val completadas: List<EntidadPartida> = emptyList(),
    val juegosMasJugados: List<JuegoPerfil> = emptyList()
)
