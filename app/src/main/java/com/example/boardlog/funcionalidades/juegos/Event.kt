package com.example.boardlog.funcionalidades.juegos

import com.example.boardlog.navegacion.Destino

sealed interface EventoJuegos {
    data class Buscar(val valor: String) : EventoJuegos;
    data object AlternarFavoritos : EventoJuegos;
    data class Abrir(val destino: Destino) : EventoJuegos
}
