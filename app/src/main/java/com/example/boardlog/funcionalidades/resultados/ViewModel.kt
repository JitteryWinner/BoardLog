package com.example.boardlog.funcionalidades.resultados

import androidx.lifecycle.viewModelScope
import com.example.boardlog.datos.ReglasPuntuacion
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ResultadosViewModel(repositorio: RepositorioBoardLog, private val idPartida: String) :
    ModeloVistaMvi<EstadoResultados, EventoResultados>(EstadoResultados()) {
    init {
        viewModelScope.launch {
            combine(
                repositorio.partidas,
                repositorio.jugadores
            ) { partidas, jugadores -> partidas.find { it.id == idPartida } to jugadores }.collect { (partida, jugadores) ->
                reducir {
                    it.copy(
                        partida = partida,
                        jugadores = jugadores,
                        clasificacion = partida?.let { ReglasPuntuacion.ordenar(it) }
                            ?.map { (id, puntos) ->
                                LineaPuntuacion(
                                    jugadores.find { it.id == id }?.nombre ?: "Jugador",
                                    id,
                                    puntos
                                )
                            } ?: emptyList(),
                        nombreGanador = jugadores.find { p -> p.id == partida?.idGanador() }?.nombre
                            ?: "Sin ganador"
                    )
                }
            }
        }
    }

    override fun alEvento(event: EventoResultados) {
        when (event) {
            EventoResultados.Inicio -> navegar(Destino.Inicio); EventoResultados.Detalle -> navegar(
            Destino.DetallePartida(idPartida)
        )
        }
    }
}
