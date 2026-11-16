package com.example.boardlog.funcionalidades.jugadores

import androidx.lifecycle.viewModelScope
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.launch

class JugadoresViewModel(repositorio: RepositorioBoardLog) :
    ModeloVistaMvi<EstadoJugadores, EventoJugadores>(EstadoJugadores()) {
    init {
        viewModelScope.launch {
            repositorio.jugadores.collect { jugadores ->
                reducir {
                    it.copy(
                        jugadores = jugadores,
                        jugadoresVisibles = jugadores.filter { p ->
                            !p.archivado && p.nombre.contains(
                                it.busqueda,
                                true
                            )
                        })
                }
            }
        }
    }

    override fun alEvento(event: EventoJugadores) {
        when (event) {
            is EventoJugadores.Buscar -> reducir {
                it.copy(
                    busqueda = event.valor,
                    jugadoresVisibles = it.jugadores.filter { p ->
                        !p.archivado && p.nombre.contains(
                            event.valor,
                            true
                        )
                    })
            }; is EventoJugadores.Abrir -> navegar(event.destino)
        }
    }
}
