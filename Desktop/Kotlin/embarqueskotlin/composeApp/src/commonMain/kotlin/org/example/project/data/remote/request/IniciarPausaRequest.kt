package org.example.project.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IniciarPausaRequest(
    @SerialName("EmbarqueID")
    val embarqueID: Int? = null,
    @SerialName("ExtensionId")
    val extensionId: Int? = null,
    @SerialName("UsuarioID")
    val usuarioID: Int?,
    @SerialName("MotivoPausa")
    val motivoPausa: String,
    @SerialName("TipoPausa")
    val tipoPausa: String
)
