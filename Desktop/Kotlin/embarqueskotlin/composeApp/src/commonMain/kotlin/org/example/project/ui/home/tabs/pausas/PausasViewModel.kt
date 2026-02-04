package org.example.project.ui.home.tabs.pausas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.request.IniciarPausaRequest
import org.example.project.domain.useCases.BuscarEmbarqueUseCase
import org.example.project.domain.useCases.FinalizarPausaUseCase
import org.example.project.domain.useCases.IniciarPausaUseCase
import org.example.project.domain.useCases.VerificarPausaActivaUseCase

class PausasViewModel(
    private val iniciarPausaUseCase: IniciarPausaUseCase,
    private val buscarEmbarqueUseCase: BuscarEmbarqueUseCase,
    private val verificarPausaActivaUseCase: VerificarPausaActivaUseCase,
    private val finalizarPausaUseCase: FinalizarPausaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PausasState())
    val state: StateFlow<PausasState> = _state.asStateFlow()

    fun cargarDatosEmbarque(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            verificarEstadoPausa(id)
            val resultado = buscarEmbarqueUseCase(id)

            resultado.onSuccess { embarque ->
                _state.update {
                    it.copy(
                        datosEmbarque = embarque, // Guarda el modelo en el estado
                        isLoading = false
                    )
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false) }
                // Maneja el error aquí si quieres
            }
        }
    }

    private fun validarEntrada() {
        _state.update { it.copy(botonConfirmar = it.tipoPausa.isNotEmpty() && it.motivoPausa.isNotBlank()) }
    }

    fun onTipoPausaSelected(tipoPausa: String) {
        _state.update { currentState ->
            val nuevoTipo = if (currentState.tipoPausa == tipoPausa) "" else tipoPausa
            currentState.copy(tipoPausa = nuevoTipo)
        }
        validarEntrada()
        if (tipoPausa != "") {
            println("$tipoPausa, aqui vemos que se escribio")
        }
    }

    fun onMotivoPausaChanged(nuevoTexto: String) {
        _state.update { it.copy(motivoPausa = nuevoTexto) }
        validarEntrada()
        if (nuevoTexto != "") {
            println("$nuevoTexto")
        }
    }

    fun iniciarPausa(
        embarqueId: Int?,
        usuarioId: Int?
    ) {
        val currentState = _state.value

        // Validaciones
        if (currentState.tipoPausa.isEmpty()) {
            _state.update { it.copy(errorMessage = "Selecciona un tipo de pausa") }
            return
        }

        if (currentState.motivoPausa.isBlank()) {
            _state.update { it.copy(errorMessage = "Escribe un motivo para la pausa") }
            return
        }

        viewModelScope.launch {
            // Estado de carga
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            println("DEBUG Enviando pausa:")
            println("  • EmbarqueID: $embarqueId")
            println("  • UsuarioID: $usuarioId")
            println("  • TipoPausa: ${currentState.tipoPausa}")
            println("  • MotivoPausa: ${currentState.motivoPausa}")

            val request = IniciarPausaRequest(
                embarqueID = embarqueId,
                extensionId = currentState.extensionId,
                usuarioID = usuarioId,
                motivoPausa = currentState.motivoPausa,
                tipoPausa = currentState.tipoPausa
            )

            val result = iniciarPausaUseCase(request)

            // Manejar resultado
            result.fold(
                onSuccess = { pausa ->
                    // Éxito: mostrar pausa creada y limpiar formulario
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            pausaIniciada = pausa,
                            motivoPausa = "",
                            tipoPausa = "",
                            botonConfirmar = false
                        )
                    }
                },
                onFailure = { error ->
                    // Error: mostrar mensaje
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Error al iniciar pausa"
                        )
                    }
                }
            )
        }
    }

    fun verificarEstadoPausa(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val resultado = verificarPausaActivaUseCase(id)

            resultado.onSuccess { data ->
                _state.update {
                    it.copy(
                        existePausaActiva = data.existeRegistro,
                        pausaIdActiva = data.pausaId,
                        isLoading = false,
                        // Si ya hay una pausa, bloqueamos el botón de iniciar
                        botonConfirmar = !data.existeRegistro
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo verificar el estado de pausa"
                    )
                }
            }
        }
    }

    fun finalizarPausa(pausaId: Int, usuarioId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val resultado = finalizarPausaUseCase(pausaId, usuarioId)

            resultado.onSuccess {
                // Al finalizar, refrescamos el estado para que el botón cambie a "Iniciar"
                _state.update {
                    it.copy(
                        isLoading = false,
                        existePausaActiva = false,
                        pausaIdActiva = null,
                        errorMessage = null,
                        operacionExitosa = true
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            }
        }
    }

}