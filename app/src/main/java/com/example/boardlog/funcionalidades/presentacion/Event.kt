package com.example.boardlog.funcionalidades.presentacion

sealed interface EventoPresentacion {
    data object Listo : EventoPresentacion
}
