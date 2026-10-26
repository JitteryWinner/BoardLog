package com.example.boardlog

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/** Prueba instrumentada que se ejecuta en un dispositivo o emulador Android. */
@RunWith(AndroidJUnit4::class)
class PruebaInstrumentadaEjemplo {
    @Test
    fun usaContextoDeLaAplicacion() {
        // Contexto de la aplicación en ejecución.
        val contextoAplicacion = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.boardlog", contextoAplicacion.packageName)
    }
}