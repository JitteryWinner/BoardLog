package com.example.boardlog.funcionalidades.resultados

sealed interface EventoResultados {
    data object Inicio : EventoResultados;
    data object Detalle : EventoResultados
}
