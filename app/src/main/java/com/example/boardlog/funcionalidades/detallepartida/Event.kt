package com.example.boardlog.funcionalidades.detallepartida

import com.example.boardlog.navegacion.Destino

sealed interface EventoDetallePartida {
    data class Abrir(val destino: Destino) : EventoDetallePartida
}
