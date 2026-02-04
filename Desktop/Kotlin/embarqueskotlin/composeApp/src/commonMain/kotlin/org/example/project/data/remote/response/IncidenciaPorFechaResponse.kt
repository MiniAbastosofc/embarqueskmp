package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class IncidenciaPorFechaResponse(
    val idIncidencia: Int,
    val embarqueId: Int,
    val idTipoIncidencia: Int,
    val cantidad: Int,
    val descripcion: String,
    val evidenciaUrl: String?,
    val estatus: String,
    val fechaRegistro: String, // Puedes usar String o kotlinx-datetime
    val fechaResolucion: String? = null,
    val usuarioRegistroId: Int,
    val usuarioResolucionId: Int? = null,
    val comentarioResolucion: String? = null,
    val ruta: String,
    val descripcionRuta: String,
    val camion: String,
    val descripcionCamion: String,
    val tipoEmbarque: String,
    val peso: Int?
)