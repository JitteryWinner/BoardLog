package com.example.boardlog.funcionalidades.historial

import androidx.lifecycle.viewModelScope
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.ESTADO_FINALIZADO
import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HistorialViewModel(repositorio: RepositorioBoardLog) :
    ModeloVistaMvi<EstadoHistorial, EventoHistorial>(EstadoHistorial()) {
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
                        partidasVisibles = filtered(partidas, jugadores, it.busqueda)
                    )
                }
            }
        }
    }

    private fun filtered(
        partidas: List<EntidadPartida>,
        jugadores: List<EntidadJugador>,
        busqueda: String
    ): List<EntidadPartida> = partidas.filter {
        it.estadoPartida == ESTADO_FINALIZADO && (busqueda.isBlank() || CatalogoJuegos.obtener(it.idJuego)?.nombre?.contains(
            busqueda,
            ignoreCase = true
        ) == true || it.ids()
            .any { id -> jugadores.any { p -> p.id == id && p.nombre.contains(busqueda, true) } })
    }

    override fun alEvento(event: EventoHistorial) {
        when (event) {
            is EventoHistorial.Buscar -> reducir {
                it.copy(
                    busqueda = event.valor,
                    partidasVisibles = filtered(it.partidas, it.jugadores, event.valor)
                )
            }; is EventoHistorial.Abrir -> navegar(event.destino)
        }
    }
}
