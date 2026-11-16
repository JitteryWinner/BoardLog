package com.example.boardlog.funcionalidades.inicio

import com.example.boardlog.navegacion.Destino

sealed interface EventoInicio {
    data class Abrir(val destino: Destino) : EventoInicio
}
