package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.IniciarPausaModel


@Serializable
data class IniciarPausaResponse(
    val success: Boolean = false,
    val message: String? = null,
    val error: String? = null, // Aquí llegará el mensaje de "Pausa activa"
    val data: PausaData? = null
)

@Serializable
data class PausaData(
    @SerialName("pausaId") val pausaId: Int? = 0,
    @SerialName("registroId") val registroId: Int? = 0,
    @SerialName("tipoRegistro") val tipoRegistro: String? = "",
    @SerialName("usuarioId") val usuarioId: Int? = 0,
    @SerialName("fechaInicio") val fechaInicio: String? = ""
)

// Función de extensión para transformar al dominio
fun IniciarPausaResponse.toDomain(): IniciarPausaModel {
    return IniciarPausaModel(
        id = data?.pausaId ?: 0,
        registroId = data?.registroId ?: 0,
        tipoRegistro = data?.tipoRegistro ?: "",
        usuarioId = data?.usuarioId ?: 0,
        fechaInicio = data?.fechaInicio ?: "",
        motivo = "", // Se puede llenar desde el request en el UseCase
        tipo = ""
    )
}