package org.example.project.ui.home.tabs.productividad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.useCases.GetProductividadUseCase

class ProductividadViewModel(
    private val getProductividadUseCase: GetProductividadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductividadState())
    val state = _state.asStateFlow()

    fun cargarReporte(inicio: String, fin: String) {
        println("DEBUG: Cargando reporte de $inicio a $fin")
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val resultado = getProductividadUseCase(inicio, fin)

            resultado.onSuccess { lista ->
                println("DEBUG: Registros recibidos: ${lista.size}")
                _state.update {
                    it.copy(
                        estibadores = lista.filter { it.tipoPersonal == "ESTIBADOR" },
                        checadores = lista.filter { it.tipoPersonal == "CHECADOR" },
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                println("DEBUG: Error en API: ${error.message}")
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun onFechaInicioChanged(nuevaFecha: String) {
        _state.update { it.copy(fechaInicio = nuevaFecha) }
        // Opcional: Si ya hay fecha fin, cargamos el reporte automáticamente
        verificarYCargar()
    }

    fun onFechaFinChanged(nuevaFecha: String) {
        _state.update { it.copy(fechaFin = nuevaFecha) }
        // Opcional: Si ya hay fecha inicio, cargamos el reporte automáticamente
        verificarYCargar()
    }

    private fun verificarYCargar() {
        val s = _state.value
        if (s.fechaInicio.isNotEmpty() && s.fechaFin.isNotEmpty()) {
            cargarReporte(s.fechaInicio, s.fechaFin)
        }
    }
}