package com.example.boardlog.funcionalidades.presentacion

import com.example.boardlog.datos.*
import com.example.boardlog.navegacion.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class PresentacionViewModel : ModeloVistaMvi<EstadoPresentacion, EventoPresentacion>(EstadoPresentacion()) {
    init { viewModelScope.launch { kotlinx.coroutines.delay(900); alEvento(EventoPresentacion.Listo) } }
    override fun alEvento(event: EventoPresentacion) { if (event == EventoPresentacion.Listo) { reducir{ it.copy(completo = true) }; navegar(Destino.Inicio) } }
}
