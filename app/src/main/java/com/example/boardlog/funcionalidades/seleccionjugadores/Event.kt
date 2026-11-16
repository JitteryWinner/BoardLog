package com.example.boardlog.funcionalidades.seleccionjugadores

import com.example.boardlog.navegacion.Destino

sealed interface EventoSeleccionJugadores {
    data class Buscar(val valor: String) : EventoSeleccionJugadores;
    data class Alternar(val id: String) : EventoSeleccionJugadores;
    data object Siguiente : EventoSeleccionJugadores;
    data class Abrir(val destino: Destino) : EventoSeleccionJugadores
}
