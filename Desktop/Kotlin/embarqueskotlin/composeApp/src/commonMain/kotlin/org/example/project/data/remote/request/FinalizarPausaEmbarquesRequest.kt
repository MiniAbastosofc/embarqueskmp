package org.example.project.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FinalizarPausaEmbarquesRequest(
    @SerialName("PausaId") val PausaId: Int,
    @SerialName("UsuarioID") val UsuarioId: Int //
)
