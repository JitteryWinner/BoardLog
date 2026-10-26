package com.example.boardlog.funcionalidades.presentacion

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boardlog.diseno.*
import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect

@Composable
fun PantallaPresentacion(modeloVista: PresentacionViewModel = viewModel(), irA: (Destino) -> Unit) {
    val estado by modeloVista.estado.collectAsStateWithLifecycle()
    LaunchedEffect(modeloVista) { modeloVista.navegacion.collect { irA(it) } }
    if (!estado.completo) com.example.boardlog.interfaz.PresentacionVisual()
}
