package com.example.boardlog.funcionalidades.partidaencurso

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

sealed interface EventoPartidaEnCurso {data class CambiarPuntos(val idJugador:String,val delta:Int):EventoPartidaEnCurso;data object Guardar:EventoPartidaEnCurso;data object Resultados:EventoPartidaEnCurso;data object Finalizar:EventoPartidaEnCurso}
