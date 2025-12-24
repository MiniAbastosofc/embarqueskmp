package org.example.project.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrearUsuarioRequest(
    @SerialName("nombre") val nombre: String,
    @SerialName("apellido") val apellido: String,
    @SerialName("numeroEmpleado") val numeroEmpleado: String,
    @SerialName("usuario") val usuario: String,
    @SerialName("password") val password: String,
    @SerialName("rolID") val rolID: Int,
    @SerialName("usuarioCreacion") val usuarioCreacion: String
)