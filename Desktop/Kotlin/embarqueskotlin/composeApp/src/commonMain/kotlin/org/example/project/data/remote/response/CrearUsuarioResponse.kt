package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.CrearUsuarioModel

@Serializable
data class CrearUsuarioResponse(
    @SerialName("message") val message: String,
    @SerialName("user") val user: UserData? = null,
    @SerialName("exito") val exito: Boolean = false // Si tu API incluye este campo
) {
    // Función de mapeo al dominio similar a LoginResponse
    fun toDomain(): CrearUsuarioModel {
        return (user?.toDomain() ?: CrearUsuarioModel(
            mensaje = message,
            exito = exito
        )) as CrearUsuarioModel
    }
}
