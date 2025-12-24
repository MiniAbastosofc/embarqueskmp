package org.example.project.ui.home.tabs.incidencias


import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.UserDataManager
import org.example.project.data.remote.request.CrearIncidenciaRequest
import org.example.project.domain.Repository

class IncidenciasViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(IncidenciasState())
    val state: StateFlow<IncidenciasState> = _state
    val user = UserDataManager.getCurrentUser()


    init {
        obtenerTiposDeIncidencias()
    }

    fun tipoIncidenciaSeleccionada(tipo: String, id: Int) {
        _state.update { it.copy(incidenciaSeleccionada = tipo) }
        if (tipo != "") {
            println("$tipo , Probando tipo")
        }
        _state.update { it.copy(idIncidencia = id) }
    }

    fun descripcionIncidenciaTexto(descripcionIncidencia: String) {
        _state.update { it.copy(descripcionIncidencia = descripcionIncidencia) }
        if (descripcionIncidencia != "") {
            println("$descripcionIncidencia probando texto de incidencia")
        }
    }

    fun accionTomadaDescripcion(accionTomadaTexto: String) {
        _state.update { it.copy(accionTomadaTexto = accionTomadaTexto) }
        if (accionTomadaTexto != "") {
            println("$accionTomadaTexto, Aqui accion tomada")
        }
    }

    private fun obtenerTiposDeIncidencias() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val tipos = repository.obtenerTipoIncidenciasApiService()
                println("✅ Tipos cargados: ${tipos.size} elementos")

                _state.update { state ->
                    state.copy(
                        tiposDeIncidencias = tipos,
                        isLoading = false,
                        // Seleccionar el primero por defecto si la lista no está vacía
                        incidenciaSeleccionada = if (tipos.isNotEmpty() && state.incidenciaSeleccionada.isEmpty()) {
                            tipos[0].codigo
                        } else {
                            state.incidenciaSeleccionada
                        }
                    )
                }
            } catch (e: Exception) {
                println("❌ Error cargando tipos de incidencias: ${e.message}")
                _state.update { state ->
                    state.copy(
                        error = e.message ?: "Error desconocido al cargar tipos",
                        isLoading = false,
                    )
                }
            }
        }

    }

    fun actualizarImagen(bitmap: ImageBitmap?) {
        _state.update { state ->
            state.copy(imagenCapturada = bitmap)
        }
        println("✅ Imagen actualizada: ${if (bitmap != null) "con imagen" else "sin imagen"}")
    }

    fun limpiarImagen() {
        _state.update { state ->
            state.copy(imagenCapturada = null)
        }
        println("🔄 Imagen limpiada")
    }

    fun enviarIncidencia() {
        val currentState = _state.value

        if (currentState.incidenciaSeleccionada.isEmpty()) {
            _state.update { it.copy(error = "Selecciona un tipo de incidencia") }
            return
        }
        if (currentState.descripcionIncidencia.isEmpty()) {
            _state.update { it.copy(error = "Agrega una descripción") }
            return
        }
        if (currentState.accionTomadaTexto.isEmpty()) {
            _state.update { it.copy(error = "Agrega la acción tomada") }
            return
        }

        val request = CrearIncidenciaRequest(
            EmbarqueId = 1,
            IdTipoIncidencia = state.value.idIncidencia,
            Cantidad = 1,
            Descripcion = state.value.descripcionIncidencia,
            Evidencia = "asdasd",
            Resolucion = state.value.accionTomadaTexto,
            UsuarioRegistroId = user?.UsuarioId
        )

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val result = repository.crearincidenciaApiService(request)
                println("✅ Incidencia creada con ID: ${result.NuevaIncidenciaId}")
                _state.update {
                    it.copy(isLoading = false)
                }
            } catch (e: Exception) {
                println("❌ Error enviando incidencia: ${e.message}")
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error al enviar incidencia"
                    )
                }
            }
        }

        println("✅ Enviando incidencia...")
        println("Tipo: ${currentState.incidenciaSeleccionada}")
        println("IdTipo: ${currentState.idIncidencia}")
        println("Descripción: ${currentState.descripcionIncidencia}")
        println("Accion Tomada: ${currentState.accionTomadaTexto}")
        println("Usuario: ${user?.UsuarioId}")
    }
}