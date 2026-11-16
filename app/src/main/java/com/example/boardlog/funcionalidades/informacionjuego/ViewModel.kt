package com.example.boardlog.funcionalidades.informacionjuego

import androidx.lifecycle.viewModelScope
import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.InformacionRemotaJuego
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.launch

class InformacionJuegoViewModel(
    private val repositorio: RepositorioBoardLog,
    private val idJuego: String
) : ModeloVistaMvi<EstadoInformacionJuego, EventoInformacionJuego>(
    EstadoInformacionJuego(
        juego = CatalogoJuegos.obtener(
            idJuego
        )
    )
) {
    init {
        viewModelScope.launch {
            repositorio.partidas.collect { partidas ->
                reducir {
                    it.copy(
                        partidas = partidas.filter { it.idJuego == idJuego })
                }
            }
        }; alEvento(EventoInformacionJuego.Cargar)
    }

    override fun alEvento(event: EventoInformacionJuego) {
        when (event) {
            EventoInformacionJuego.Cargar -> viewModelScope.launch {
                val info = InformacionRemotaJuego.buscarDescripcion(
                    CatalogoJuegos.obtener(idJuego)?.nombre ?: idJuego
                ); reducir { it.copy(informacionExterna = info, cargando = false) }
            }

            EventoInformacionJuego.Iniciar -> {
                repositorio.reiniciarSeleccion(); navegar(Destino.SeleccionJugadores(idJuego))
            }

            is EventoInformacionJuego.Abrir -> navegar(event.destino)
        }
    }
}
