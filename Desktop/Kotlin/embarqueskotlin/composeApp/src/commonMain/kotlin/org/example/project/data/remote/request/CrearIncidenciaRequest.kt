package org.example.project.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CrearIncidenciaRequest(
    val EmbarqueId: Int,
    val IdTipoIncidencia: Int,
    val Cantidad: Int,
    val Descripcion: String,
    val Evidencia: String,
    val UsuarioRegistroId: Int?,
    val Resolucion: String
)