package org.example.project.ui.home.tabs.incidencias

import androidx.compose.ui.graphics.ImageBitmap
import org.example.project.data.remote.response.IncidenciaPorFechaResponse
import org.example.project.domain.model.TipoIncidenciasModel

data class IncidenciasState(
    val incidenciaSeleccionada: String = "",
    val idIncidencia: Int = 1,
    val descripcionIncidencia: String = "",
    val accionTomadaTexto: String = "",
    val tiposDeIncidencias: List<TipoIncidenciasModel>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val imagenCapturada: ImageBitmap? = null,
    val nuevaIncidenciaId: Int = 0,
    val evidenciaUri: String? = null,
    val operacionExitosa: Boolean = false,
    val embarqueId: String = "",
    val listaIncidencias: List<IncidenciaPorFechaResponse> = emptyList(), // <-- Nueva propiedad
)