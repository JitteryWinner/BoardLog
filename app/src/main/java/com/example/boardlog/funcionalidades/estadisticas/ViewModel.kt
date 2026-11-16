package com.example.boardlog.funcionalidades.estadisticas

import androidx.lifecycle.viewModelScope
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class EstadisticasViewModel(private val repositorio: RepositorioBoardLog) :
    ModeloVistaMvi<EstadoEstadisticas, EventoEstadisticas>(EstadoEstadisticas()) {
    init {
        viewModelScope.launch {
            combine(
                repositorio.partidas,
                repositorio.jugadores
            ) { partidas, jugadores -> partidas to jugadores }
                .collect { (partidas, jugadores) ->
                    reducir { anterior ->
                        val seleccionado = anterior.idJugadorSeleccionado
                            ?.takeIf { id -> jugadores.any { it.id == id } }
                            ?: jugadores.firstOrNull()?.id
                        calcular(
                            anterior.copy(
                                partidas = partidas,
                                jugadores = jugadores,
                                idJugadorSeleccionado = seleccionado
                            )
                        )
                    }
                }
        }
    }

    private fun calcular(estado: EstadoEstadisticas): EstadoEstadisticas {
        val finalizadas = CalculosEstadisticas.filtrarPorFecha(estado.partidas, estado.filtro)
        val propias =
            CalculosEstadisticas.partidasJugador(finalizadas, estado.idJugadorSeleccionado)
        val juegos = propias.groupBy { it.idJuego }.entries.sortedByDescending { it.value.size }
            .mapNotNull { (idJuego, partidas) ->
                CatalogoJuegos.obtener(idJuego)?.let { juego ->
                    EstadisticaJuego(
                        juego,
                        partidas.size,
                        CalculosEstadisticas.victorias(partidas, estado.idJugadorSeleccionado)
                    )
                }
            }
        return estado.copy(
            partidasFiltradas = propias,
            jugadas = propias.size,
            victorias = CalculosEstadisticas.victorias(propias, estado.idJugadorSeleccionado),
            empates = propias.count { it.idGanador() == null },
            derrotas = propias.count { it.idGanador() != null && it.idGanador() != estado.idJugadorSeleccionado },
            porcentajeVictorias = CalculosEstadisticas.porcentajeVictorias(
                propias,
                estado.idJugadorSeleccionado
            ),
            juegosMasJugados = juegos
        )
    }

    override fun alEvento(event: EventoEstadisticas) {
        when (event) {
            is EventoEstadisticas.Filtrar -> reducir { calcular(it.copy(filtro = event.valor)) }
            is EventoEstadisticas.SeleccionarJugador -> reducir { anterior ->
                if (anterior.jugadores.none { it.id == event.id }) anterior
                else calcular(anterior.copy(idJugadorSeleccionado = event.id))
            }

            is EventoEstadisticas.Abrir -> navegar(event.destino)
        }
    }
}
