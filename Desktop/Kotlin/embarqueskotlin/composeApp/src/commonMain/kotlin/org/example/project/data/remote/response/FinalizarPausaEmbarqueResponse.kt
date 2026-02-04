package org.example.project.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class FinalizarPausaEmbarqueResponse(
    val success: Boolean,
    val message: String? = null,
    val error: String? = null,
    val data: FinalizarPausaData? = null
)

@Serializable
data class FinalizarPausaData(
    val pausaId: Int,
    val registroId: Int?,
    val tipoRegistro: String?,
    val usuarioId: Int,
    val fechaInicio: String,
    val fechaFin: String,
    val duracionMinutos: Int,
    val estado: String
)