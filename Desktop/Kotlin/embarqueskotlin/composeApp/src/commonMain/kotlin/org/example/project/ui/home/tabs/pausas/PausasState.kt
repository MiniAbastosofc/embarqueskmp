package org.example.project.ui.home.tabs.pausas

import org.example.project.domain.model.IniciarPausaModel
import org.example.project.domain.model.ObtenerEmbarquePorIdModel

data class PausasState(
    val prueba: String = "",
    val tipoPausa: String = "",
    val motivoPausa: String = "",
    val botonConfirmar: Boolean = false,

    val embarqueId: Int? = null,
    val extensionId: Int? = null,
    val usuarioId: Int = 0,

    // Estados de la operación
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val pausaIniciada: IniciarPausaModel? = null,

    val datosEmbarque: ObtenerEmbarquePorIdModel? = null,

    val existePausaActiva: Boolean = false,
    val pausaIdActiva: Int? = null,
    val operacionExitosa: Boolean = false,
)