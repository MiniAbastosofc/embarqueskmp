package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgregarFotoResponse(
    @SerialName("message") val message: String
)