package com.example.boardlog.funcionalidades.presentacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.navegacion.Destino

@Composable
fun PantallaPresentacion(modeloVista: PresentacionViewModel = viewModel(), irA: (Destino) -> Unit) {
    val estado by modeloVista.estado.collectAsStateWithLifecycle()
    LaunchedEffect(modeloVista) { modeloVista.navegacion.collect { irA(it) } }
    if (!estado.completo) com.example.boardlog.interfaz.PresentacionVisual()
}
