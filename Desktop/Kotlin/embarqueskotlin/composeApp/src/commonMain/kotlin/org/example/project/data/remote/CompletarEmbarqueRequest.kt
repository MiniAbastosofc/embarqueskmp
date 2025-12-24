package org.example.project.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletarEmbarqueRequest(
    @SerialName("EmbarqueID") val embarqueID: Int,
    @SerialName("UsuarioID") val usuarioID: Int
)