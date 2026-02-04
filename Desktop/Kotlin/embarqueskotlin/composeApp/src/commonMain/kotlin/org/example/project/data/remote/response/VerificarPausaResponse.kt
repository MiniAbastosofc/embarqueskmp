package org.example.project.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class VerificarPausaResponse(
    val message: String,
    val data: VerificarPausaData
)

@Serializable
data class VerificarPausaData(
    val pausaId: Int? = null,
    val existeRegistro: Boolean = false
)