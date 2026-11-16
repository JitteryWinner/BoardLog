package com.example.boardlog.funcionalidades.informacionjuego

import com.example.boardlog.navegacion.Destino

sealed interface EventoInformacionJuego {
    data object Cargar : EventoInformacionJuego;
    data object Iniciar : EventoInformacionJuego;
    data class Abrir(val destino: Destino) : EventoInformacionJuego
}
