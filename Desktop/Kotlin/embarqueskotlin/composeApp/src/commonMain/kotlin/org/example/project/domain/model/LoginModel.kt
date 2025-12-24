package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val UsuarioId: Int,
    val Nombre: String,
    val Apellido: String,
    val NumeroEmpleado: String,
    val Usuario: String,
    val RolID: Int,
    val NombreRol: String,
    val Activo: Boolean,
    val FechaCreacion: String
)