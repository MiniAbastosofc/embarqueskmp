package org.example.project.domain.model

data class CompletarEmbarqueModel(
    val message: String,
    val data: EmbarqueCompletadoModel?
)

data class EmbarqueCompletadoModel(
    val embarqueID: String,
    val fechaEmbarque: String,
    val ruta: String,
    val descripcionRuta: String,
    val camion: String,
    val descripcionCamion: String,
    val cantidadPedidos: Int,
    val estadoID: Int,
    val estado: String? = null,
    val porcentajeAvance: Int? = null,
    val fechaInicio: String? = null,
    val fechaCompletado: String? = null,
    val minutosTranscurridos: Int? = null,
    val usuarioInicio: String? = null,
    val usuarioCompletado: String? = null
)