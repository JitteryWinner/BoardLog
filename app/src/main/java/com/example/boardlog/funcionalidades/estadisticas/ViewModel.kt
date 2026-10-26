package com.example.boardlog.funcionalidades.estadisticas

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import java.util.Calendar

class EstadisticasViewModel(repositorio: RepositorioBoardLog) : ModeloVistaMvi<EstadoEstadisticas, EventoEstadisticas>(EstadoEstadisticas()) {
    init { viewModelScope.launch {
        combine(repositorio.partidas, repositorio.jugadores) { partidas, jugadores -> partidas to jugadores }.collect { (partidas,jugadores) ->
            reducir{ old -> calcular(old.copy(partidas=partidas,jugadores=jugadores),filtrar(partidas,old.filtro)) }
        }
    } }
    private fun filtrar(partidas:List<EntidadPartida>, seleccionado:FiltroFecha):List<EntidadPartida> {
        val ahora=Calendar.getInstance()
        val anio=ahora.get(Calendar.YEAR)
        val mes=ahora.get(Calendar.MONTH)
        return partidas.filter { m ->
            if(m.estadoPartida!=ESTADO_FINALIZADO) false
            else if(seleccionado== FiltroFecha.TODOS) true
            else {val fecha=Calendar.getInstance().apply { timeInMillis=m.fechaCreacion }
                fecha.get(Calendar.YEAR)==anio && (seleccionado==FiltroFecha.ANIO || fecha.get(Calendar.MONTH)==mes)
            }
        }
    }
    private fun calcular(old:EstadoEstadisticas, juegos:List<EntidadPartida>):EstadoEstadisticas {
        val porcentaje=if(juegos.isEmpty())0 else juegos.count{it.idGanador()=="p1"}*100/juegos.size
        val mejores=juegos.groupBy{it.idJuego}.entries.sortedByDescending{it.value.size}.mapNotNull{(idJuego,partidas)->
            CatalogoJuegos.obtener(idJuego)?.let{EstadisticaJuego(it,partidas.size,partidas.count{m->m.idGanador()=="p1"})}
        }
        return old.copy(partidasFiltradas=juegos,jugadas=juegos.size,porcentajeVictorias=porcentaje,juegosMasJugados=mejores)
    }
    override fun alEvento(event:EventoEstadisticas) { when(event) {
        is EventoEstadisticas.Filtrar -> reducir{ old -> calcular(old.copy(filtro=event.valor),filtrar(old.partidas,event.valor)) }
        is EventoEstadisticas.Abrir -> navegar(event.destino)
    } }
}
