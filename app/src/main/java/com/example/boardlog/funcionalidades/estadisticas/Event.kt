package com.example.boardlog.funcionalidades.estadisticas

import com.example.boardlog.navegacion.Destino

sealed interface EventoEstadisticas {
    data class Filtrar(val valor: FiltroFecha) : EventoEstadisticas
    data class SeleccionarJugador(val id: String) : EventoEstadisticas
    data class Abrir(val destino: Destino) : EventoEstadisticas
}
