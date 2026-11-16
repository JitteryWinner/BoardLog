package com.example.boardlog.funcionalidades.confirmacion

sealed interface EventoConfirmacion {
    data object Iniciar : EventoConfirmacion;
    data object Cancelar : EventoConfirmacion;
    data object EditarJuego : EventoConfirmacion;
    data object EditarJugadores : EventoConfirmacion
}
