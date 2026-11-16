package com.example.boardlog.funcionalidades.jugadores

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.EntidadJugador
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.BarraBusqueda
import com.example.boardlog.diseno.Encabezado
import com.example.boardlog.diseno.ListaAplicacion
import com.example.boardlog.diseno.NaranjaBoardLog
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaJugadores(repositorio: RepositorioBoardLog, irA: (Destino) -> Unit) {
    val alcance = rememberCoroutineScope()
    var mostrarNuevo by remember { mutableStateOf(false) }
    var nombreNuevo by remember { mutableStateOf("") }
    var errorNuevo by remember { mutableStateOf<String?>(null) }
    var seleccionado by remember { mutableStateOf<EntidadJugador?>(null) }
    var editar by remember { mutableStateOf(false) }
    var confirmarEliminar by remember { mutableStateOf(false) }
    var errorAccion by remember { mutableStateOf<String?>(null) }
    val snackbar = remember { SnackbarHostState() }
    if (editar && seleccionado != null) AlertDialog(
        onDismissRequest = { editar = false },
        title = { Text("Editar jugador") },
        text = {
            Column {
                OutlinedTextField(
                    nombreNuevo,
                    { nombreNuevo = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )
                errorAccion?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                alcance.launch {
                    errorAccion = repositorio.editarJugador(seleccionado!!.id, nombreNuevo)
                    if (errorAccion == null) editar = false
                }
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = { editar = false }) { Text("Cancelar") } })
    if (confirmarEliminar && seleccionado != null) AlertDialog(
        onDismissRequest = {
            confirmarEliminar = false
        },
        title = { Text("Eliminar jugador") },
        text = { Text("¿Eliminar a ${seleccionado!!.nombre} de tu lista? Sus partidas anteriores seguirán en el historial.") },
        confirmButton = {
            TextButton(onClick = {
                alcance.launch {
                    val fallo = repositorio.eliminarJugador(seleccionado!!.id)
                    confirmarEliminar = false
                    if (fallo != null) snackbar.showSnackbar(fallo)
                }
            }) { Text("Eliminar") }
        },
        dismissButton = {
            TextButton(onClick = {
                confirmarEliminar = false
            }) { Text("Cancelar") }
        })

    if (mostrarNuevo) AlertDialog(
        onDismissRequest = { mostrarNuevo = false },
        title = { Text("Agregar jugador") },
        text = {
            Column {
                OutlinedTextField(
                    nombreNuevo,
                    { nombreNuevo = it },
                    label = { Text("Nombre del jugador") },
                    singleLine = true
                )
                errorNuevo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                alcance.launch {
                    errorNuevo = repositorio.agregarJugador(nombreNuevo)
                    if (errorNuevo == null) {
                        nombreNuevo = ""; mostrarNuevo = false
                    }
                }
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = { mostrarNuevo = false }) { Text("Cancelar") } })
    val modeloVista: JugadoresViewModel =
        viewModel(factory = FabricaViewModel { JugadoresViewModel(repositorio) });
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle()
    LaunchedEffect(modeloVista) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(alNavegar = { modeloVista.alEvento(EventoJugadores.Abrir(it)) }) {
        Encabezado("Jugadores")
        SnackbarHost(hostState = snackbar)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    modeloVista.alEvento(
                        EventoJugadores.Abrir(Destino.Juegos)
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = NaranjaBoardLog)
            ) { Text("Juegos") }; OutlinedButton(onClick = {}) { Text("Jugadores") }
        }
        Button(
            onClick = { nombreNuevo = ""; errorNuevo = null; mostrarNuevo = true },
            modifier = Modifier.fillMaxWidth()
        ) { Icon(Icons.Default.Add, null); Spacer(Modifier.width(6.dp)); Text("Agregar jugador") }
        BarraBusqueda(
            estadoPantalla.busqueda,
            "Buscar jugador",
            { modeloVista.alEvento(EventoJugadores.Buscar(it)) })
        ListaAplicacion {
            val visibles = estadoPantalla.jugadoresVisibles
            if (visibles.isEmpty()) item { Text("No hay jugadores disponibles.") }
            items(
                visibles,
                key = { it.id }) { p ->
                TarjetaBoardLog(onClick = {
                    modeloVista.alEvento(
                        EventoJugadores.Abrir(
                            Destino.PerfilJugador(
                                p.id
                            )
                        )
                    )
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.AccountCircle,
                            null,
                            modifier = Modifier.size(44.dp),
                            tint = NaranjaBoardLog
                        ); Spacer(Modifier.width(10.dp)); Text(
                        p.nombre,
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    ); Row {
                        IconButton(onClick = {
                            seleccionado = p; nombreNuevo = p.nombre; errorAccion = null; editar =
                            true
                        }) { Icon(Icons.Default.Edit, "Editar jugador") }; IconButton(onClick = {
                        seleccionado = p; confirmarEliminar = true
                    }) { Icon(Icons.Default.Delete, "Eliminar jugador") }; Icon(
                        Icons.Default.ArrowForward,
                        null
                    )
                    }
                    }
                }
            }
        }
    }
}
