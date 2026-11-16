package com.example.boardlog.funcionalidades.seleccionjugadores

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.diseno.BarraBusqueda
import com.example.boardlog.diseno.BotonPrincipal
import com.example.boardlog.diseno.ListaAplicacion
import com.example.boardlog.diseno.NaranjaBoardLog
import com.example.boardlog.diseno.PaginaBoardLog
import com.example.boardlog.diseno.RojoBoardLog
import com.example.boardlog.diseno.TarjetaBoardLog
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.FabricaViewModel
import kotlinx.coroutines.launch

@Composable
fun PantallaSeleccionJugadores(
    repositorio: RepositorioBoardLog,
    idJuego: String,
    irA: (Destino) -> Unit,
    alVolver: () -> Unit
) {
    var agregar by remember { mutableStateOf(false) };
    var nuevo by remember { mutableStateOf("") };
    var errorNuevo by remember { mutableStateOf<String?>(null) }
    val alcance = rememberCoroutineScope()
    if (agregar) AlertDialog(
        onDismissRequest = { agregar = false },
        title = { Text("Nuevo jugador") },
        text = {
            Column {
                OutlinedTextField(nuevo, { nuevo = it }, label = { Text("Nombre") })
                errorNuevo?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                alcance.launch {
                    errorNuevo = repositorio.agregarJugador(nuevo); if (errorNuevo == null) {
                    nuevo = ""; agregar = false
                }
                }
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = { agregar = false }) { Text("Cancelar") } })
    val modeloVista: SeleccionJugadoresViewModel =
        viewModel(factory = FabricaViewModel { SeleccionJugadoresViewModel(repositorio, idJuego) });
    val estadoPantalla by modeloVista.estado.collectAsStateWithLifecycle(); LaunchedEffect(
        modeloVista
    ) { modeloVista.navegacion.collect { irA(it) } }
    PaginaBoardLog(
        title = "Nueva partida",
        back = true,
        alVolver = alVolver,
        alNavegar = { modeloVista.alEvento(EventoSeleccionJugadores.Abrir(it)) }) {
        OutlinedButton(onClick = { agregar = true }, modifier = Modifier.fillMaxWidth()) {
            Icon(
                Icons.Default.Add,
                null
            ); Text(" Agregar jugador")
        }
        BarraBusqueda(
            estadoPantalla.busqueda,
            "Buscar jugador",
            { modeloVista.alEvento(EventoSeleccionJugadores.Buscar(it)) })
        Text("Jugadores", fontWeight = FontWeight.Bold, fontSize = 19.sp)
        Spacer(Modifier.height(10.dp))
        // El espacio flexible mantiene visibles los botones incluso con muchos jugadores.
        ListaAplicacion(Modifier.weight(1f)) {
            items(estadoPantalla.jugadoresVisibles, key = { it.id }) { p ->
                TarjetaBoardLog(onClick = { modeloVista.alEvento(EventoSeleccionJugadores.Alternar(p.id)) }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AccountCircle,
                            null,
                            Modifier.size(38.dp),
                            tint = NaranjaBoardLog
                        ); Spacer(Modifier.width(10.dp)); Text(
                        p.nombre,
                        Modifier.weight(1f)
                    ); Checkbox(
                        checked = p.id in estadoPantalla.seleccionados,
                        onCheckedChange = { modeloVista.alEvento(EventoSeleccionJugadores.Alternar(p.id)) })
                    }
                }
            }
        }
        Text(
            "${estadoPantalla.seleccionados.size} jugadores seleccionados",
            fontWeight = FontWeight.Bold
        )
        Text(
            "${estadoPantalla.juego?.nombre ?: "Juego"} requiere entre ${estadoPantalla.juego?.minimoJugadores ?: 0} y ${estadoPantalla.juego?.maximoJugadores ?: 0} jugadores.",
            fontSize = 12.sp
        )
        estadoPantalla.error?.let { Text(it, color = RojoBoardLog) }
        Spacer(Modifier.height(10.dp)); BotonPrincipal(
        "Siguiente",
        enabled = estadoPantalla.puedeContinuar
    ) { modeloVista.alEvento(EventoSeleccionJugadores.Siguiente) }
    }
}
