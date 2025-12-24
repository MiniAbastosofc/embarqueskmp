package org.example.project.ui.home.tabs.sucursales

import org.example.project.ui.home.tabs.rutas.UploadFotoState

data class SucursalesState(
    val fechaSeleccionada: String? = null,
    val searchText: String = "",
    val comentario: String = "",
    val imagenUri: String? = null,
    val porcentajeAvance: Int = 0,
    val uploadFotoState: UploadFotoState = UploadFotoState.Idle,
    val embarqueFinalizado: Boolean = false,
    val prueba: String = ""
)