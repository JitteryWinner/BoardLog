package com.example.boardlog.navegacion

import kotlinx.serialization.Serializable

sealed interface Destino {
    @Serializable data object Presentacion : Destino
    @Serializable data object Inicio : Destino
    @Serializable data object Juegos : Destino
    @Serializable data class InformacionJuego(val idJuego: String) : Destino
    @Serializable data object Jugadores : Destino
    @Serializable data class PerfilJugador(val idJugador: String) : Destino
    @Serializable data object SeleccionJuego : Destino
    @Serializable data class SeleccionJugadores(val idJuego: String) : Destino
    @Serializable data class ConfirmacionPartida(val idJuego: String) : Destino
    @Serializable data class PartidaEnCurso(val idPartida: String) : Destino
    @Serializable data class Resultados(val idPartida: String) : Destino
    @Serializable data object Historial : Destino
    @Serializable data class DetallePartida(val idPartida: String) : Destino
    @Serializable data object Estadisticas : Destino
}
