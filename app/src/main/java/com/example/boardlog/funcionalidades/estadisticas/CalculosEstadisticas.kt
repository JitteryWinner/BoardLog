package com.example.boardlog.funcionalidades.estadisticas

import com.example.boardlog.datos.ESTADO_FINALIZADO
import com.example.boardlog.datos.EntidadPartida
import java.util.Calendar

/** Cálculos independientes de la interfaz, fáciles de verificar mediante tests. */
object CalculosEstadisticas {
    fun filtrarPorFecha(
        partidas: List<EntidadPartida>,
        filtro: FiltroFecha,
        ahora: Calendar = Calendar.getInstance()
    ): List<EntidadPartida> {
        return partidas.filter { partida ->
            if (partida.estadoPartida != ESTADO_FINALIZADO) return@filter false
            if (filtro == FiltroFecha.TODOS) return@filter true
            val fecha = Calendar.getInstance().apply { timeInMillis = partida.fechaCreacion }
            fecha.get(Calendar.YEAR) == ahora.get(Calendar.YEAR) &&
                    (filtro == FiltroFecha.ANIO || fecha.get(Calendar.MONTH) == ahora.get(Calendar.MONTH))
        }
    }

    fun partidasJugador(
        partidasFinalizadas: List<EntidadPartida>,
        idJugador: String?
    ): List<EntidadPartida> =
        if (idJugador == null) emptyList() else partidasFinalizadas.filter { idJugador in it.ids() }

    fun victorias(partidasJugador: List<EntidadPartida>, idJugador: String?): Int =
        if (idJugador == null) 0 else partidasJugador.count { it.idGanador() == idJugador }

    fun porcentajeVictorias(partidasJugador: List<EntidadPartida>, idJugador: String?): Int =
        if (partidasJugador.isEmpty()) 0 else victorias(
            partidasJugador,
            idJugador
        ) * 100 / partidasJugador.size
}
