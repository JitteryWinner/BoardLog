package com.example.boardlog.funcionalidades.seleccionjuego

import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.ModeloVistaMvi

class SeleccionJuegoViewModel(private val repositorio: RepositorioBoardLog) :
    ModeloVistaMvi<EstadoSeleccionJuego, EventoSeleccionJuego>(EstadoSeleccionJuego()) {
    override fun alEvento(event: EventoSeleccionJuego) {
        when (event) {
            is EventoSeleccionJuego.Buscar -> reducir {
                it.copy(
                    busqueda = event.valor,
                    juegosVisibles = it.juegos.filter { g -> g.nombre.contains(event.valor, true) })
            }; is EventoSeleccionJuego.Elegir -> {
            repositorio.reiniciarSeleccion(); navegar(Destino.SeleccionJugadores(event.idJuego))
        }; is EventoSeleccionJuego.Abrir -> navegar(event.destino)
        }
    }
}
