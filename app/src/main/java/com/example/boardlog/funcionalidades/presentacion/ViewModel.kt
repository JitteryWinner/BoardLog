package com.example.boardlog.funcionalidades.presentacion

import androidx.lifecycle.viewModelScope
import com.example.boardlog.navegacion.Destino
import com.example.boardlog.navegacion.ModeloVistaMvi
import kotlinx.coroutines.launch

class PresentacionViewModel :
    ModeloVistaMvi<EstadoPresentacion, EventoPresentacion>(EstadoPresentacion()) {
    init {
        viewModelScope.launch { kotlinx.coroutines.delay(900); alEvento(EventoPresentacion.Listo) }
    }

    override fun alEvento(event: EventoPresentacion) {
        if (event == EventoPresentacion.Listo) {
            reducir { it.copy(completo = true) }; navegar(Destino.Inicio)
        }
    }
}
