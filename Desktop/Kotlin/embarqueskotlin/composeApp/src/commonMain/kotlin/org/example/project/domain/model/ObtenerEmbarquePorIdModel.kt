package org.example.project.domain.model

data class ObtenerEmbarquePorIdModel(
    val id: Int,
    val ruta: String,
    val descripcionRuta: String,
    val camion: String,
    val descripcionCamion: String,
    val sucursal: String,
    val estadoId: Int,
    val tipoEmbarque: String,
    val almOrigen: String?,
    val desAlmOrigen: String?,
    val almDestino: String?,
    val desAlmDestino: String?
    )