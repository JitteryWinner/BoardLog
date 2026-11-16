package com.example.boardlog.funcionalidades.historial

import com.example.boardlog.navegacion.Destino

sealed interface EventoHistorial {
    data class Buscar(val valor: String) : EventoHistorial;
    data class Abrir(val destino: Destino) : EventoHistorial
}
