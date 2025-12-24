package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FotosRutasResponse(
    @SerialName("fotoID") val fotoId: Int,
    @SerialName("urlFoto") val urlFoto: String,
    @SerialName("comentario") val comentario: String? = null,
    @SerialName("porcentajeAvance") val porcentajeAvance: Int,
    @SerialName("fechaCreacion") val fechaCreacion: String,
    @SerialName("usuarioCreacion") val usuarioCreacion: String,
    @SerialName("nombreUsuarioCreacion") val nombreUsuarioCreacion: String
)