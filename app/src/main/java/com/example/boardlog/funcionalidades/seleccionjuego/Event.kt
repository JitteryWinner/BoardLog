package com.example.boardlog.funcionalidades.seleccionjuego

import com.example.boardlog.navegacion.Destino

sealed interface EventoSeleccionJuego {
    data class Buscar(val valor: String) : EventoSeleccionJuego;
    data class Elegir(val idJuego: String) : EventoSeleccionJuego;
    data class Abrir(val destino: Destino) : EventoSeleccionJuego
}
