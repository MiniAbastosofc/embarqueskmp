package org.example.project.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IniciarPausaEmbarqueRequest(
    @SerialName("EmbarqueID")
    val EmbarqueID: Int? = null,

    @SerialName("ExtensionId")
    val ExtensionId: Int? = null,

    @SerialName("UsuarioID")
    val UsuarioID: Int? = null,

    @SerialName("MotivoPausa")
    val MotivoPausa: String,

    @SerialName("TipoPausa")
    val TipoPausa: String
)