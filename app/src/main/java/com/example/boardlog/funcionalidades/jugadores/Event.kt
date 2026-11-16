package com.example.boardlog.funcionalidades.jugadores

import com.example.boardlog.navegacion.Destino

sealed interface EventoJugadores {
    data class Buscar(val valor: String) : EventoJugadores;
    data class Abrir(val destino: Destino) : EventoJugadores
}
