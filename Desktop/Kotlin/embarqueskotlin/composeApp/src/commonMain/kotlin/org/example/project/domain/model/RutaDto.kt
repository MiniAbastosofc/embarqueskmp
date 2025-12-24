package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RutaDto(
    val id: Int,
    val tipo: String,
    val titulo: String,
    val subtitulo: String,
    val detalle: String,
    val estado: Int,
    val porcentaje: Int,
    val ruta: String? = null,
    val camion: String? = null,
    val origen: String? = null,
    val destino: String? = null,
    val cantidad: Int
) {
    fun toDomain(): RutasModel {
        return RutasModel(
            id = id,
            tipo = tipo,
            titulo = titulo,
            subtitulo = subtitulo,
            detalle = detalle,
            estado = estado,
            porcentaje = porcentaje,
            ruta = ruta,
            camion = camion,
            origen = origen,
            destino = destino,
            cantidad = cantidad
        )
    }
}