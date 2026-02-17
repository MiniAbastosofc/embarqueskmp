package org.example.project.features.devolucion.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.ui.home.tabs.pausas.PausasState

class DevolucionViewModel() : ViewModel() {
    private val _state = MutableStateFlow(DevolucionState())
    val state: StateFlow<DevolucionState> = _state.asStateFlow()

    fun onMotivoTexto(nuevoTexto: String) {
        _state.update { it.copy(prueba = nuevoTexto) }
        if (nuevoTexto != "") {
            println(nuevoTexto)
        }
    }

    fun onBuscarFolioDevolucion(nuevoTexto: String) {
        _state.update { it.copy(prueba = nuevoTexto) }
        if (nuevoTexto != "") {
            println(nuevoTexto)
        }
    }
}