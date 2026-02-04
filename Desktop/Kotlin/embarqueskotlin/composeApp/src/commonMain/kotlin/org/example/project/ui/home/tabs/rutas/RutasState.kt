package org.example.project.ui.home.tabs.rutas

import org.example.project.domain.model.PersonalBodegaModel
import org.example.project.domain.model.RutasModel

data class RutasState(
    val fechaSeleccionada: String? = null,
    val searchText: String = "",
    val comentario: String = "",
    val imagenUri: String? = null,
    val embarqueFinalizado: Boolean = false,
    val embarques: List<RutasModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentRutaStatus: Int? = null,
    val porcentajeAvance: Int = 0,
    val iniciarRutasState: RutaUiState = RutaUiState.Idle,
    val uploadFotoState: UploadFotoState = UploadFotoState.Idle,
    val finalizandoEmbarque: Boolean = false,
    val finalizarEmbarqueError: String? = null,
    val currentEmbarqueID: String? = null,
    val currentUsuarioID: Int? = null,
    val embarquesFiltrados: List<RutasModel> = emptyList(),
    val filtroEstado: EstadoRuta? = null,
    val checadores: List<PersonalBodegaModel> = emptyList(),
    val estibadores: List<PersonalBodegaModel> = emptyList(),
)

enum class EstadoRuta(val valor: Int) {
    PENDIENTE(1),
    EN_PROCESO(2),
    COMPLETADO(3);

    companion object {
        fun fromInt(value: Int): EstadoRuta? {
            return values().find { it.valor == value }
        }
    }
}