package org.example.project.ui.home.tabs.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.GetShipmentDetailsByDateUseCase
import org.example.project.domain.GetShipmentDetailsByIdUseCase
import org.example.project.domain.model.RutaDetallesModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class HistorialViewModel(
    private val getShipmentDetailsByDateUseCase: GetShipmentDetailsByDateUseCase,
    private val getShipmentDetailsByIdUseCase: GetShipmentDetailsByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HistorialState())
    val state: StateFlow<HistorialState> = _state

    fun onDateSelected(yyyymmdd: String?) {
        _state.update { it.copy(fechaSeleccionada = yyyymmdd) }

        // Cargar los datos inmediatamente después de seleccionar una fecha válida
        if (yyyymmdd != null) {
            loadShipmentsForDate(yyyymmdd)
        } else {
            // Limpiar lista si la fecha es nula
            _state.update { it.copy(shipments = emptyList()) }
        }
    }

    private fun loadShipmentsForDate(date: String? = null) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, shipments = emptyList()) }
            try {
                // 1. Determinar la fecha a usar (fecha del parámetro o fecha de hoy)
                val fecha = date ?: _state.value.fechaSeleccionada ?: run {
                    val now = Clock.System.now()
                    val zone = TimeZone.currentSystemDefault()
                    val hoy: LocalDate = now.toLocalDateTime(zone).date
                    hoy.toString().replace("-", "")
                }

                // Si la API solo devuelve un resultado, lo manejamos como una lista de 1 elemento.
                val shipmentDetails: List<RutaDetallesModel> =
                    getShipmentDetailsByDateUseCase(fecha)

                _state.update {
                    it.copy(
                        isLoading = false,
                        // Colocamos el resultado individual dentro de una lista para el LazyColumn
                        shipments = shipmentDetails,
                        fechaSeleccionada = fecha // Actualizar la fecha en el estado
                    )
                }
            } catch (e: Exception) {
                // Manejo de errores de red o del repositorio (como credenciales incorrectas)
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun loadShipmentDetails(shipmentId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, details = null) }
            try {
                val details = getShipmentDetailsByIdUseCase(shipmentId)
                _state.update { it.copy(isLoading = false, details = details) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun onComentarioChange(newComentario: String) {
        _state.value = _state.value.copy(prueba = newComentario)
    }
}