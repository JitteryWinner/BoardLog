package com.example.boardlog.funcionalidades.perfiljugador

import com.example.boardlog.navegacion.Destino

sealed interface EventoPerfilJugador {
    data class Abrir(val destino: Destino) : EventoPerfilJugador
}
