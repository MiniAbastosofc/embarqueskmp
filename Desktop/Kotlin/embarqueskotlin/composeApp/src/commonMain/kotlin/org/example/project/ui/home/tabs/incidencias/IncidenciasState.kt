package org.example.project.ui.home.tabs.incidencias

import androidx.compose.ui.graphics.ImageBitmap
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
    val nuevaIncidenciaId: Int = 0
)