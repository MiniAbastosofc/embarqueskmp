package org.example.project.ui.home.tabs.incidencias


import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.convertPathToBase64
import org.example.project.data.UserDataManager
import org.example.project.data.remote.request.CrearIncidenciaRequest
import org.example.project.domain.Repository
import org.example.project.domain.useCases.GetIncidenciasUseCase
import org.example.project.utils.convertirUriABase64Platform
import kotlin.io.encoding.Base64

class IncidenciasViewModel(
    private val repository: Repository,
    private val getIncidenciasUseCase: GetIncidenciasUseCase
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

    fun setEvidenciaUri(uri: String?) {
        _state.update { it.copy(evidenciaUri = uri) }
    }

    fun setEmbarqueId(id: String) {
        _state.update { it.copy(embarqueId = id) }
    }

    fun enviarIncidencia(pathImagen: String?) {
        val currentState = _state.value
        // 1. Validaciones
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

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // CONVERSIÓN: Aquí conviertes el path en el String real de la imagen
                val imagenBytes: ByteArray? = pathImagen?.let { path ->
                    val base64String = convertPathToBase64(path)
                    Base64.decode(base64String) // Convertir base64 a ByteArray
                }

                if (imagenBytes == null) {
                    _state.update {
                        it.copy(
                            error = "No se pudo cargar la imagen",
                            isLoading = false
                        )
                    }
                    return@launch
                }

                val request = CrearIncidenciaRequest(
                    EmbarqueId = currentState.embarqueId.toIntOrNull()
                        ?: 0, // ¿De dónde obtienes este ID?
                    IdTipoIncidencia = currentState.idIncidencia,
                    Cantidad = 1,
                    Descripcion = currentState.descripcionIncidencia,
                    Evidencia = imagenBytes, // Ahora es ByteArray
                    Resolucion = currentState.accionTomadaTexto,
                    UsuarioRegistroId = user?.UsuarioId ?: 0 // Asegúrate de que no sea null
                )

                repository.crearincidenciaApiService(request)
                _state.update { it.copy(isLoading = false, operacionExitosa = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
    fun cargarIncidenciasPorFecha(fecha: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getIncidenciasUseCase(fecha)

            result.onSuccess { lista ->
                _state.update { it.copy(
                    listaIncidencias = lista,
                    isLoading = false
                ) }
            }.onFailure { exception ->
                _state.update { it.copy(
                    error = exception.message ?: "Error desconocido",
                    isLoading = false
                ) }
            }
        }
    }
}