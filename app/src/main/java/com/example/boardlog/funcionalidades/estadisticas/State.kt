package com.example.boardlog.funcionalidades.estadisticas

import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.JuegoMesa

enum class FiltroFecha { MES, ANIO, TODOS }

data class EstadisticaJuego(val juego: JuegoMesa, val partidas: Int, val victorias: Int)

data class EstadoEstadisticas(
    val filtro: FiltroFecha = FiltroFecha.TODOS,
    val idJugadorSeleccionado: String? = null,
    val partidas: List<EntidadPartida> = emptyList(),
    val partidasFiltradas: List<EntidadPartida> = emptyList(),
    val jugadores: List<EntidadJugador> = emptyList(),
    val jugadas: Int = 0,
    val porcentajeVictorias: Int = 0,
    val victorias: Int = 0,
    val derrotas: Int = 0,
    val empates: Int = 0,
    val juegosMasJugados: List<EstadisticaJuego> = emptyList()
)
