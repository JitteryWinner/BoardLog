package com.example.boardlog

import com.example.boardlog.datos.*
import org.junit.Assert.*
import org.junit.Test

class PruebaReglasPuntuacion {
    private fun partida(juego: String, puntos: String) = EntidadPartida(
        "test", juego, "a,b", puntos, ESTADO_FINALIZADO, 100L
    )

    @Test fun catanGanaMayorPuntaje() {
        assertEquals("a", partida("catan", "a:10,b:7").idGanador())
    }

    @Test fun unoGanaMenorPenalizacion() {
        assertEquals("b", partida("uno", "a:50,b:10").idGanador())
    }

    @Test fun empateNoTieneGanadorAutomatico() {
        assertNull(partida("catan", "a:10,b:10").idGanador())
    }

    @Test fun ajedrezConGanadorRegistraUnaVictoria() {
        assertEquals("b", partida("ajedrez", "a:0,b:1").idGanador())
    }

    @Test fun ajedrezEnTablasNoRegistraGanador() {
        assertNull(partida("ajedrez", "a:0,b:0").idGanador())
    }

    @Test fun partidaSinFinalizarNoDaGanador() {
        val enCurso = EntidadPartida("partida", "catan", "a,b", "a:10,b:5", ESTADO_EN_CURSO, 100L)
        assertNull(enCurso.idGanador())
    }

    @Test fun monopolyUsaIncrementosDeCien() {
        assertEquals(100, ReglasPuntuacion.obtener("monopoly").paso)
    }
}
