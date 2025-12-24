package org.example.project.ui.home.tabs.rutas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.AgregarFotoUseCase
import org.example.project.domain.CompletarEmbarqueUseCase
//import kotlinx.datetime.*
import org.example.project.domain.GetRutasUseCase
import org.example.project.domain.IniciarRutaUseCase
import org.example.project.domain.model.RutasModel
import kotlin.time.Clock // <-- Usa este Clock
import kotlin.time.ExperimentalTime

//@OptIn(ExperimentalTime::class)
@OptIn(ExperimentalTime::class)
class RutasViewModel(
    private val getRutasUseCase: GetRutasUseCase,
    private val iniciarRutaUseCase: IniciarRutaUseCase,
    private val agregarFotoUseCase: AgregarFotoUseCase,
    private val completarEmbarqueUseCase: CompletarEmbarqueUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(RutasState())
    val state: StateFlow<RutasState> = _state
    private val _iniciarRutaState = MutableStateFlow<RutaUiState>(RutaUiState.Idle)
    val iniciarRutaState: StateFlow<RutaUiState> = _iniciarRutaState.asStateFlow()
    private val _uploadFotoState = MutableStateFlow<UploadFotoState>(UploadFotoState.Idle)
    val uploadFotoState: StateFlow<UploadFotoState> = _uploadFotoState.asStateFlow()

    fun onDateSelected(yyyymmdd: String?) {
        _state.update { it.copy(fechaSeleccionada = yyyymmdd) }
        if (yyyymmdd != null) {
            loadEmbarques(yyyymmdd)
        }
    }

    fun loadEmbarques(fechaParametro: String? = null) {
//        println("DEBUG_VM: Entró a loadEmbarques(fechaParametro=$fechaParametro)")
        viewModelScope.launch {
//            println("DEBUG_VM: Entró al viewModelScope.launch")
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // 1. Determinar la fecha a usar (fecha del parámetro o fecha de hoy)
                val fecha = fechaParametro ?: _state.value.fechaSeleccionada ?: run {

                    val now = Clock.System.now()
                    val zone = TimeZone.currentSystemDefault()
                    val hoy: LocalDate = now.toLocalDateTime(zone).date
                    hoy.toString().replace("-", "")
                }
//                println("DEBUG_VM: Fecha final a usar = $fecha")
                val resultList = getRutasUseCase.invoke(fecha)
//                println("LOG_RUTAS: Datos recibidos para la fecha $fecha. Total de rutas: ${resultList.size}")
//                println("DEBUG_VM: UseCase regresó ${resultList.size} elementos")
                _state.update {
                    it.copy(
                        isLoading = false,
                        embarques = resultList,
                        fechaSeleccionada = fecha,
                        embarquesFiltrados = aplicarFiltros(resultList, it)
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error desconocido al cargar rutas",
                        embarquesFiltrados = emptyList()
                    )
                }
            }
        }
    }

    fun onSearchTextChanged(newText: String) {
        _state.update { currentState ->
            val nuevoState = currentState.copy(searchText = newText)
            nuevoState.copy(
                embarquesFiltrados = aplicarFiltros(nuevoState.embarques, nuevoState)
            )
        }
    }

    fun onComentarioChange(newComentario: String) {
        println("📝 Comentario actualizado: $newComentario")
        _state.value = _state.value.copy(comentario = newComentario)
    }

    fun onImagenCapturada(uri: String) {
        println("📸 Imagen capturada: $uri")
        _state.value = _state.value.copy(imagenUri = uri)
    }

    fun finalizarEmbarque() {
        println("✅ Embarque finalizado - Estado completo: ${_state.value}")
        _state.value = _state.value.copy(embarqueFinalizado = true)
    }

    fun setCurrentRutaStatus(status: Int) {
        _state.update { it.copy(currentRutaStatus = status) }
    }

    fun agregarFoto(
        embarqueID: Int,
        comentario: String?,
        porcentajeAvance: Int,
        usuarioID: Int?,
        filePath: String
    ) {
        viewModelScope.launch {
            _state.update { it.copy(uploadFotoState = UploadFotoState.Loading) }
            try {
                when (val result = agregarFotoUseCase(
                    embarqueID = embarqueID,
                    comentario = comentario,
                    porcentajeAvance = porcentajeAvance,
                    usuarioID = usuarioID,
                    filePath = filePath
                )) {
                    is AgregarFotoUseCase.Result.Success -> {
                        _state.update {
                            it.copy(
                                uploadFotoState = UploadFotoState.Success(result.message),
                                porcentajeAvance = porcentajeAvance, // Mantener el porcentaje
                                comentario = "", // ✅ REINICIAR COMENTARIO
                                imagenUri = null // ✅ REINICIAR IMAGEN
                            )
                        }
                        println("✅ Foto enviada exitosamente - Comentario e imagen reiniciados")
                    }

                    is AgregarFotoUseCase.Result.Error -> {
                        _state.update {
                            it.copy(uploadFotoState = UploadFotoState.Error(result.message))
                            // ❌ NO reiniciar en caso de error
                        }
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        uploadFotoState = UploadFotoState.Error(
                            e.message ?: "Error desconocido al subir foto"
                        )
                    )
                }
            }
        }
    }

    fun iniciarRuta(embarqueId: Int, usuarioId: Int?) {
        viewModelScope.launch {
            _iniciarRutaState.value = RutaUiState.Loading
            try {
                val message = iniciarRutaUseCase(embarqueId, usuarioId)
                _iniciarRutaState.value = RutaUiState.Success(message)
                _state.update { it.copy(currentRutaStatus = 2) }
                loadEmbarques(_state.value.fechaSeleccionada)

            } catch (e: Exception) {
                _iniciarRutaState.value = RutaUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }


    fun setProgress(percentage: Int) {
        _state.update { it.copy(porcentajeAvance = percentage.coerceIn(0, 100)) }
    }

    fun resetIniciarRutaState() {
        _iniciarRutaState.value = RutaUiState.Idle
    }

    fun resetUploadFotoState() {
        _state.update {
            it.copy(uploadFotoState = UploadFotoState.Idle)
        }
    }

    // NUEVO: Función para finalizar embarque
    fun finalizarEmbarque(embarqueID: String?, usuarioID: Int?) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    finalizandoEmbarque = true,
                    finalizarEmbarqueError = null
                )
            }

            try {
                val result = completarEmbarqueUseCase(embarqueID, usuarioID)
                result.fold(
                    onSuccess = { response ->
                        _state.update {
                            it.copy(
                                finalizandoEmbarque = false,
                                embarqueFinalizado = true,
                                currentRutaStatus = 3, // COMPLETADO
                                currentEmbarqueID = embarqueID,
                                currentUsuarioID = usuarioID
                            )
                        }
                        println("✅ Embarque $embarqueID finalizado exitosamente: ${response.message}")
                    },
                    onFailure = { exception ->
                        val errorMessage = when {
                            exception.message?.contains("en proceso", ignoreCase = true) == true ->
                                "No se puede finalizar. El embarque debe estar en proceso."

                            exception.message?.contains(
                                "no encontrado",
                                ignoreCase = true
                            ) == true ->
                                "Usuario no encontrado."

                            else -> exception.message ?: "Error al finalizar el embarque"
                        }
                        _state.update {
                            it.copy(
                                finalizandoEmbarque = false,
                                finalizarEmbarqueError = errorMessage
                            )
                        }
                        println("❌ Error al finalizar embarque: $errorMessage")
                    }
                )
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        finalizandoEmbarque = false,
                        finalizarEmbarqueError = e.message ?: "Error desconocido"
                    )
                }
                println("❌ Error inesperado: ${e.message}")
            }
        }
    }

    // Limpiar error de finalización
    fun clearFinalizarError() {
        _state.update { it.copy(finalizarEmbarqueError = null) }
    }

    // Setear embarque actual
    fun setCurrentEmbarque(embarqueID: String, usuarioID: Int) {
        _state.update {
            it.copy(
                currentEmbarqueID = embarqueID,
                currentUsuarioID = usuarioID
            )
        }
    }

    fun onEstadoSelected(estado: EstadoRuta?) {
        _state.update { currentState ->
            val nuevoState = currentState.copy(filtroEstado = estado)
            nuevoState.copy(
                embarquesFiltrados = aplicarFiltros(nuevoState.embarques, nuevoState)
            )
        }
    }

    fun limpiarFiltros() {
        _state.update { currentState ->
            val nuevoState = currentState.copy(
                searchText = "",
                filtroEstado = null
                // Mantenemos la fecha seleccionada
            )
            nuevoState.copy(
                embarquesFiltrados = aplicarFiltros(nuevoState.embarques, nuevoState)
            )
        }
    }

    private fun aplicarFiltros(
        embarques: List<RutasModel>,
        state: RutasState
    ): List<RutasModel> {
        return embarques
            .filter { ruta ->
                // Filtro por búsqueda de texto
                state.searchText.isEmpty() ||
                        ruta.titulo?.contains(state.searchText, ignoreCase = true) == true ||
                        ruta.subtitulo?.contains(state.searchText, ignoreCase = true) == true ||
                        ruta.camion?.contains(state.searchText, ignoreCase = true) == true ||
                        ruta.origen?.contains(state.searchText, ignoreCase = true) == true ||
                        ruta.destino?.contains(state.searchText, ignoreCase = true) == true
            }
            .filter { ruta ->
                // Filtro por estado
                state.filtroEstado?.let { estado ->
                    ruta.estado == estado.valor
                } ?: true
            }
            .sortedByDescending { it.id } // O el criterio que prefieras
    }

}

sealed interface RutaUiState {
    object Idle : RutaUiState
    object Loading : RutaUiState
    data class Success(val message: String) : RutaUiState
    data class Error(val errorMessage: String) : RutaUiState
}

sealed class UploadFotoState {
    object Idle : UploadFotoState()
    object Loading : UploadFotoState()
    data class Success(val message: String) : UploadFotoState()
    data class Error(val error: String) : UploadFotoState()
}