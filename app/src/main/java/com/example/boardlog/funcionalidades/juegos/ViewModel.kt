package com.example.boardlog.funcionalidades.juegos

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*

class JuegosViewModel: ModeloVistaMvi<EstadoJuegos,EventoJuegos>(EstadoJuegos()) {
    private fun filtrar(estadoPantalla:EstadoJuegos):List<JuegoMesa> = estadoPantalla.juegos.filter {
        it.nombre.contains(estadoPantalla.busqueda,ignoreCase=true) && (!estadoPantalla.soloFavoritos || it.id in listOf("catan","uno","risk"))
    }
    override fun alEvento(event:EventoJuegos) { when(event) {
        is EventoJuegos.Buscar -> reducir{ old -> old.copy(busqueda=event.valor).let { it.copy(juegosVisibles=filtrar(it)) } }
        EventoJuegos.AlternarFavoritos -> reducir{ old -> old.copy(soloFavoritos=!old.soloFavoritos).let { it.copy(juegosVisibles=filtrar(it)) } }
        is EventoJuegos.Abrir -> navegar(event.destino)
    } }
}
