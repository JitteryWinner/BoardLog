package com.example.boardlog.navegacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** StateFlow para estados persistentes; Channel para navegación de un solo uso. */
abstract class ModeloVistaMvi<S : Any, E : Any>(initial: S) : ViewModel() {
    private val _estado = MutableStateFlow(initial)
    val estado = _estado.asStateFlow()
    private val _navegacion = Channel<Destino>(Channel.BUFFERED)
    val navegacion = _navegacion.receiveAsFlow()
    protected fun reducir(block: (S) -> S) { _estado.update(block) }
    protected fun navegar(destino: Destino) { viewModelScope.launch { _navegacion.send(destino) } }
    abstract fun alEvento(event: E)
}

class FabricaViewModel<VM : ViewModel>(private val createVm: () -> VM) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = createVm() as T
}
