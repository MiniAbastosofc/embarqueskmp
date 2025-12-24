package org.example.project.ui.home.tabs.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.CrearUsuarioRequest
import org.example.project.domain.CrearUsuarioUseCase

class AdminViewModel(
    private val crearUsuarioUseCase: CrearUsuarioUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AdminState())
    val state: StateFlow<AdminState> = _state.asStateFlow()

    fun onEvent(event: AdminEvent) {
        when (event) {
            is AdminEvent.CrearUsuarioNombreChanged -> {
                _state.update { it.copy(crearUsuarioNombre = event.nombre) }
                generarUsuarioId()
            }

            is AdminEvent.CrearUsuarioApellidoChanged -> {
                _state.update { it.copy(crearUsuarioApellido = event.apellido) }
                generarUsuarioId()
            }

            is AdminEvent.CrearUsuarioNumeroEmpleadoChanged -> {
                _state.update { it.copy(crearUsuarioNumeroEmpleado = event.numeroEmpleado) }
                generarUsuarioId()
            }

            is AdminEvent.CrearUsuarioRolChanged -> {
                _state.update { it.copy(crearUsuarioRol = event.rol) }
            }

            is AdminEvent.CrearUsuarioContrasenaChanged -> {
                _state.update { it.copy(crearUsuarioContrasena = event.contrasena) }
            }

            is AdminEvent.CrearUsuarioMostrarContrasenaChanged -> {
                _state.update { it.copy(crearUsuarioMostrarContrasena = event.mostrar) }
            }

            is AdminEvent.CrearUsuarioErrorChanged -> {
                _state.update { it.copy(crearUsuarioError = event.error) }
            }

            AdminEvent.CrearUsuario -> {
                crearUsuario()
            }

            AdminEvent.LimpiarFormulario -> {
                limpiarFormulario()
            }
            // Tus otros eventos existentes...
            is AdminEvent.CambiarEstadoAdmin -> {
                _state.update { it.copy(isAdmin = event.isAdmin) }
            }
        }
    }

    private fun generarUsuarioId() {
        val state = _state.value
        val usuarioId = if (state.crearUsuarioNombre.isNotEmpty() &&
            state.crearUsuarioApellido.isNotEmpty() &&
            state.crearUsuarioNumeroEmpleado.isNotEmpty()
        ) {

            val inicialNombre = state.crearUsuarioNombre.take(1).uppercase()
            val inicialApellido = state.crearUsuarioApellido.take(1).uppercase()
            val numero = state.crearUsuarioNumeroEmpleado.padStart(4, '0')
            "$inicialNombre$inicialApellido$numero"
        } else {
            ""
        }
        _state.update { it.copy(crearUsuarioId = usuarioId) }
    }

    private fun crearUsuario() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    crearUsuarioLoading = true,
                    crearUsuarioError = null
                )
            }

            try {
                // Validar formulario antes de enviar
                if (!isFormularioValido(_state.value)) {
                    _state.update {
                        it.copy(
                            crearUsuarioLoading = false,
                            crearUsuarioError = "Por favor completa todos los campos correctamente"
                        )
                    }
                    return@launch
                }

                // Mapear rol a ID
                val rolID = when (_state.value.crearUsuarioRol) {
                    "Administrador" -> 1
                    "Auditor" -> 2
                    else -> 0
                }

                // Generar nombre de usuario automáticamente
                val usuario = generarNombreUsuario(
                    _state.value.crearUsuarioNombre,
                    _state.value.crearUsuarioApellido
                )

                val request = CrearUsuarioRequest(
                    nombre = _state.value.crearUsuarioNombre.trim(),
                    apellido = _state.value.crearUsuarioApellido.trim(),
                    numeroEmpleado = _state.value.crearUsuarioNumeroEmpleado.trim(),
                    usuario = usuario,
                    password = _state.value.crearUsuarioContrasena,
                    rolID = rolID,
                    usuarioCreacion = "admin" // Cambiar por usuario actual logueado
                )

                // Llamar al UseCase real
                val result = crearUsuarioUseCase(request)

                result.fold(
                    onSuccess = { userModel ->
                        _state.update {
                            it.copy(
                                crearUsuarioLoading = false,
                                crearUsuarioId = userModel.usuarioId?.toString()
                                    ?: "Usuario creado exitosamente",
                                crearUsuarioError = null
                            )
                        }

                        // Limpiar formulario después de 3 segundos
                        viewModelScope.launch {
                            delay(3000)
                            limpiarFormulario()
                        }
                    },
                    onFailure = { error ->
                        _state.update {
                            it.copy(
                                crearUsuarioLoading = false,
                                crearUsuarioError = error.message ?: "Error al crear usuario"
                            )
                        }
                    }
                )

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        crearUsuarioLoading = false,
                        crearUsuarioError = "Error inesperado: ${e.message}"
                    )
                }
            }
        }
    }

    private fun generarNombreUsuario(nombre: String, apellido: String): String {
        val nombreBase = nombre.lowercase()
            .replace(" ", "")
            .replace(Regex("[^a-záéíóúñ]"), "")
            .take(10)
        val apellidoBase = apellido.lowercase()
            .split(" ").first()
            .replace(Regex("[^a-záéíóúñ]"), "")
            .take(8)
        return "$nombreBase.$apellidoBase"
    }

    private fun limpiarFormulario() {
        _state.update {
            it.copy(
                crearUsuarioNombre = "",
                crearUsuarioApellido = "",
                crearUsuarioNumeroEmpleado = "",
                crearUsuarioRol = "",
                crearUsuarioContrasena = "",
                crearUsuarioId = "",
                crearUsuarioMostrarContrasena = false,
                crearUsuarioError = null
            )
        }
    }

    private fun isFormularioValido(state: AdminState): Boolean {
        return state.crearUsuarioNombre.isNotBlank() &&
                state.crearUsuarioApellido.isNotBlank() &&
                state.crearUsuarioNumeroEmpleado.isNotBlank() &&
                state.crearUsuarioRol.isNotBlank() &&
                state.crearUsuarioContrasena.isNotBlank() &&
                state.crearUsuarioContrasena.length >= 6
    }
}

// Eventos
sealed class AdminEvent {
    data object CrearUsuario : AdminEvent()
    data object LimpiarFormulario : AdminEvent()
    data class CrearUsuarioNombreChanged(val nombre: String) : AdminEvent()
    data class CrearUsuarioApellidoChanged(val apellido: String) : AdminEvent()
    data class CrearUsuarioNumeroEmpleadoChanged(val numeroEmpleado: String) : AdminEvent()
    data class CrearUsuarioRolChanged(val rol: String) : AdminEvent()
    data class CrearUsuarioContrasenaChanged(val contrasena: String) : AdminEvent()
    data class CrearUsuarioMostrarContrasenaChanged(val mostrar: Boolean) : AdminEvent()
    data class CrearUsuarioErrorChanged(val error: String?) : AdminEvent()

    // Tus eventos existentes...
    data class CambiarEstadoAdmin(val isAdmin: Boolean) : AdminEvent()
}