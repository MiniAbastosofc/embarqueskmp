package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IniciarRutaResponse(
    @SerialName("message") val message: String
){}

@Serializable
data class IniciarRutaPayload(
    val EmbarqueID: Int,
    val UsuarioID: Int?
)