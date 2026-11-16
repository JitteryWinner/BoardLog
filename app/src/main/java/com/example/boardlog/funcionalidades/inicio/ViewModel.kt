package com.example.boardlog.funcionalidades.inicio

import androidx.lifecycle.viewModelScope
import com.example.boardlog.datos.ESTADO_FINALIZADO
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class InicioViewModel(private val repositorio: RepositorioBoardLog) :
    ModeloVistaMvi<EstadoInicio, EventoInicio>(EstadoInicio()) {
    init {
        viewModelScope.launch {
            combine(
                repositorio.partidas,
                repositorio.jugadores
            ) { partidas, jugadores -> partidas to jugadores }.collect { (partidas, jugadores) ->
                reducir {
                    it.copy(
                        partidas = partidas,
                        jugadores = jugadores,
                        cantidadFinalizadas = partidas.count { m -> m.estadoPartida == ESTADO_FINALIZADO },
                        porcentajeVictorias = 0
                    )
                }
            }
        }
    }

    override fun alEvento(event: EventoInicio) {
        when (event) {
            is EventoInicio.Abrir -> navegar(event.destino)
        }
    }
}
