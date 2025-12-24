package org.example.project.ui.home.tabs.admin

data class AdminState(
    val crearUsuarioNombre: String = "",
    val crearUsuarioApellido: String = "",
    val crearUsuarioNumeroEmpleado: String = "",
    val crearUsuarioRol: String = "",
    val crearUsuarioContrasena: String = "",
    val crearUsuarioMostrarContrasena: Boolean = false,
    val crearUsuarioLoading: Boolean = false,
    val crearUsuarioId: String = "",
    val crearUsuarioError: String? = null,
    // Tus estados existentes...
    val isAdmin: Boolean = false
)