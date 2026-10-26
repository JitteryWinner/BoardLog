package com.example.boardlog.funcionalidades.informacionjuego

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class InformacionJuegoViewModel(private val repositorio:RepositorioBoardLog,private val idJuego:String):ModeloVistaMvi<EstadoInformacionJuego,EventoInformacionJuego>(EstadoInformacionJuego(juego=CatalogoJuegos.obtener(idJuego))) {
 init { viewModelScope.launch { repositorio.partidas.collect { partidas -> reducir{ it.copy(partidas=partidas.filter { it.idJuego==idJuego }) } } }; alEvento(EventoInformacionJuego.Cargar) }
 override fun alEvento(event:EventoInformacionJuego) { when(event) {
    EventoInformacionJuego.Cargar -> viewModelScope.launch { val info=InformacionRemotaJuego.buscarDescripcion(CatalogoJuegos.obtener(idJuego)?.nombre?:idJuego);reducir{ it.copy(informacionExterna=info,cargando=false) } }
    EventoInformacionJuego.Iniciar -> {repositorio.reiniciarSeleccion();navegar(Destino.SeleccionJugadores(idJuego))}
    is EventoInformacionJuego.Abrir -> navegar(event.destino)
 } }
}
