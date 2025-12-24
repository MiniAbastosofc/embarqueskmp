package org.example.project.ui.home.tabs.sucursales

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SucursalesViewModel() : ViewModel() {
    private val _state = MutableStateFlow(SucursalesState())
    val state: StateFlow<SucursalesState> = _state

    fun onDateSelected(yyyymmdd: String?) {
        _state.update { it.copy(fechaSeleccionada = yyyymmdd) }
        if (yyyymmdd != null) {
//            loadEmbarques(yyyymmdd)
            println(yyyymmdd)
        }
    }

    fun onSearchTextChanged(newText: String) {
        _state.update { it.copy(searchText = newText) }
    }

    fun onImagenCapturada(uri: String) {
        println("📸 Imagen capturada: $uri")
        _state.value = _state.value.copy(imagenUri = uri)
    }

    fun setProgress(percentage: Int) {
        _state.update { it.copy(porcentajeAvance = percentage.coerceIn(0, 100)) }
    }

    fun onComentarioChange(newComentario: String) {
        println("📝 Comentario actualizado: $newComentario")
        _state.value = _state.value.copy(comentario = newComentario)
    }

}