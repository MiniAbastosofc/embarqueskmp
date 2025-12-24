package org.example.project.domain.model

data class CrearUsuarioModel(
    val usuarioId: Int? = null,
    val nombre: String = "",
    val apellido: String = "",
    val numeroEmpleado: String = "",
    val usuario: String = "",
    val rolID: Int = 0,
    val nombreRol: String = "",
    val activo: Boolean = false,
    val fechaCreacion: String = "",
    val mensaje: String = "",
    val exito: Boolean = false
)