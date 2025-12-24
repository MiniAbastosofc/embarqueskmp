package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RutaInfoResponse(
    @SerialName("embarqueID") val embarqueID: Int,
    @SerialName("fechaEmbarque") val fechaEmbarque: String,
    @SerialName("tipoEmbarque") val tipoEmbarque: String,
    @SerialName("rutaID") val rutaID: String? = null,
    @SerialName("nombreRuta") val nombreRuta: String? = null,
    @SerialName("camionID") val camionID: String? = null,
    @SerialName("nombreCamion") val nombreCamion: String? = null,
    @SerialName("cantidadPedidos") val cantidadPedidos: Int,

    @SerialName("almOrigen") val almOrigen: String?,
    @SerialName("desAlmOrigen") val desAlmOrigen: String?,
    @SerialName("almDestino") val almDestino: String?,
    @SerialName("desAlmDestino") val desAlmDestino: String?,
    @SerialName("totalArticulos") val totalArticulos: Int?,

    @SerialName("estadoID") val estadoID: Int,
    @SerialName("estado") val estado: String,
    @SerialName("porcentajeAvance") val porcentajeAvance: Int,

    @SerialName("fechaInicio") val fechaInicio: String?,
    @SerialName("usuarioInicio") val usuarioInicio: String?,
    @SerialName("nombreUsuarioInicio") val nombreUsuarioInicio: String?,

    @SerialName("fechaCompletado") val fechaCompletado: String?,
    @SerialName("usuarioCompletado") val usuarioCompletado: String?,
    @SerialName("nombreUsuarioCompletado") val nombreUsuarioCompletado: String?,

    @SerialName("duracionMinutos") val duracionMinutos: Int?,
    @SerialName("duracionFormateada") val duracionFormateada: String,

    @SerialName("fechaCreacion") val fechaCreacion: String,
    @SerialName("usuarioCreacion") val usuarioCreacion: String
)