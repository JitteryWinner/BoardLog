package com.example.boardlog.funcionalidades.sesion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.boardlog.datos.RepositorioBoardLog
import com.example.boardlog.navegacion.FabricaViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SesionViewModel(private val repo: RepositorioBoardLog) : ViewModel() {
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val _ocupado = MutableStateFlow(false)
    val ocupado = _ocupado.asStateFlow()
    fun entrar(usuario: String, pin: String, crear: Boolean, listo: () -> Unit) =
        viewModelScope.launch {
            _ocupado.value = true
            try {
                val resultado = repo.entrar(usuario, pin, crear)
                _error.value = resultado
                if (resultado == null) listo()
            } catch (error: Exception) {
                _error.value = "No se pudo guardar la sesión: ${error.message}"
            } finally {
                _ocupado.value = false
            }
        }
}

@Composable
fun PantallaSesion(repo: RepositorioBoardLog, alEntrar: () -> Unit) {
    val vm: SesionViewModel = viewModel(factory = FabricaViewModel { SesionViewModel(repo) })
    val error by vm.error.collectAsStateWithLifecycle()
    val ocupado by vm.ocupado.collectAsStateWithLifecycle()
    var usuario by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var registro by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("BoardLog", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(12.dp))
        Text(if (registro) "Crear perfil local" else "Iniciar sesión")
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = pin,
            onValueChange = { pin = it },
            label = { Text("PIN numérico (4 a 12 dígitos)") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = Modifier.fillMaxWidth()
        )
        error?.let {
            Spacer(Modifier.height(6.dp)); Text(
            it,
            color = MaterialTheme.colorScheme.error
        )
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { vm.entrar(usuario, pin, registro, alEntrar) },
            enabled = !ocupado,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (registro) "Crear cuenta" else "Entrar")
        }
        TextButton(onClick = {
            registro = !registro
        }) { Text(if (registro) "Ya tengo cuenta" else "Crear una cuenta") }
        Text(
            "Los datos se guardan en este dispositivo y se separan por cuenta.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
