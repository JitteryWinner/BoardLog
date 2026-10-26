package com.example.boardlog

import com.example.boardlog.datos.CatalogoJuegos
import com.example.boardlog.datos.EntidadPartida
import com.example.boardlog.datos.ESTADO_EN_CURSO
import com.example.boardlog.datos.ESTADO_FINALIZADO
import org.junit.Assert.*
import org.junit.Test

class PruebaDatosBoardLog {
    @Test fun elCatalogoTieneRangosValidosDeJugadores() {
        assertTrue(CatalogoJuegos.juegos.isNotEmpty())
        assertTrue(CatalogoJuegos.juegos.all { it.minimoJugadores > 0 && it.maximoJugadores >= it.minimoJugadores })
    }

    @Test fun elGanadorSeCalculaAlTerminar() {
        val partidaEnCurso = EntidadPartida("m1", "catan", "p1,p2,p3", "p1:6,p2:10,p3:8", ESTADO_EN_CURSO, 0)
        assertNull(partidaEnCurso.idGanador())
        assertEquals("p2", partidaEnCurso.copy(estadoPartida=ESTADO_FINALIZADO).idGanador())
    }

    @Test fun lasPuntuacionesSeAsocianConCadaJugador() {
        val partida = EntidadPartida("m1", "uno", "p1,p2", "p1:2,p2:7", ESTADO_FINALIZADO, 0)
        assertEquals(2, partida.ids().size)
        assertEquals(7, partida.puntos()["p2"])
    }
}
