package org.example.project.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CajasProductividadRequest(
    val fechaInicio: String,
    val fechaFin: String
)