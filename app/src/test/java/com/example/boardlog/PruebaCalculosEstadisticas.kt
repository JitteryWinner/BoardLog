package com.example.boardlog

import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.ESTADO_FINALIZADO
import com.example.boardlog.datos.ESTADO_EN_CURSO
import com.example.boardlog.funcionalidades.estadisticas.CalculosEstadisticas
import com.example.boardlog.funcionalidades.estadisticas.FiltroFecha
import org.junit.Assert.assertEquals
import org.junit.Test

class PruebaCalculosEstadisticas {
    private val partidas = listOf(
        EntidadPartida("m1", "uno", "p1,p2", "p1:5,p2:10", ESTADO_FINALIZADO, 1000L),
        EntidadPartida("m2", "catan", "p2,p3", "p2:3,p3:12", ESTADO_FINALIZADO, 2000L),
        EntidadPartida("m3", "risk", "p1,p3", "p1:2,p3:1", ESTADO_EN_CURSO, 3000L)
    )

    @Test fun estadisticasSoloIncluyenPartidasDelJugador() {
        val finalizadas = CalculosEstadisticas.filtrarPorFecha(partidas, FiltroFecha.TODOS)
        assertEquals(2, CalculosEstadisticas.partidasJugador(finalizadas, "p2").size)
        assertEquals(1, CalculosEstadisticas.partidasJugador(finalizadas, "p1").size)
    }

    @Test fun tasaDeVictoriasCorrespondeAlJugadorSeleccionado() {
        val finalizadas = CalculosEstadisticas.filtrarPorFecha(partidas, FiltroFecha.TODOS)
        val p2 = CalculosEstadisticas.partidasJugador(finalizadas, "p2")
        assertEquals(1, CalculosEstadisticas.victorias(p2, "p2"))
        assertEquals(50, CalculosEstadisticas.porcentajeVictorias(p2, "p2"))
        assertEquals(0, CalculosEstadisticas.porcentajeVictorias(emptyList(), "p1"))
    }
}
