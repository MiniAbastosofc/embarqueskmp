package org.example.project.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("usuario")
    val Usuario: String,

    @SerialName("password")
    val PasswordHash: String
)