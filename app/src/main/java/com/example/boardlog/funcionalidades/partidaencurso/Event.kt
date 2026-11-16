package com.example.boardlog.funcionalidades.partidaencurso

sealed interface EventoPartidaEnCurso {
    data class CambiarPuntos(val idJugador: String, val delta: Int) : EventoPartidaEnCurso;
    data object Guardar : EventoPartidaEnCurso;
    data object Resultados : EventoPartidaEnCurso;
    data object Finalizar : EventoPartidaEnCurso
}
