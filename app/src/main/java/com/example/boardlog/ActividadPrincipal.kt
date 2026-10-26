package com.example.boardlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.boardlog.navegacion.NavegacionBoardLog
import com.example.boardlog.interfaz.tema.TemaBoardLog

class ActividadPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repositorio = (application as AplicacionBoardLog).repositorio
        setContent { TemaBoardLog { NavegacionBoardLog(repositorio) } }
    }
}
