package org.example.project.ui.home.tabs.historial

import embarqueskotlin.composeapp.generated.resources.Res
import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.domain.model.RutaDetallesModel

data class HistorialState(
    val fechaSeleccionada: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val shipments: List<RutaDetallesModel> = emptyList(),
    val isLoadingDetails: Boolean = false,
    val details: DetalleEmbarqueCompleto? = null,
    val selectedShipmentDetails: RutaDetallesModel? = null,
    val prueba: String = "",
)