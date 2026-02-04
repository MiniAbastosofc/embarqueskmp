package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.ObtenerEmbarquePorIdModel

@Serializable
data class BuscarEmbarquePorIdResponse(
    val data: EmbarqueData
) {
    fun toDomain(): ObtenerEmbarquePorIdModel {
        return ObtenerEmbarquePorIdModel(
            id = data.embarqueId,
            ruta = data.ruta,
            descripcionRuta = data.descripcionRuta,
            camion = data.camion,
            descripcionCamion = data.descripcionCamion,
            sucursal = data.sucursal,
            estadoId = data.estadoId,
            tipoEmbarque = data.tipoEmbarque,
            almOrigen = data.almOrigen,
            desAlmOrigen = data.desAlmOrigen,
            almDestino = data.almDestino,
            desAlmDestino = data.desAlmDestino
        )
    }
}

@Serializable
data class EmbarqueData(
    @SerialName("EmbarqueID") val embarqueId: Int,
    @SerialName("Ruta") val ruta: String,
    @SerialName("DescripcionRuta") val descripcionRuta: String,
    @SerialName("Camion") val camion: String,
    @SerialName("DescripcionCamion") val descripcionCamion: String,
    @SerialName("Sucursal") val sucursal: String,
    @SerialName("EstadoID") val estadoId: Int,
    @SerialName("TipoEmbarque") val tipoEmbarque: String,

    // Usamos String? porque en tu JSON vienen como null
    @SerialName("AlmOrigen") val almOrigen: String? = null,
    @SerialName("DesAlmOrigen") val desAlmOrigen: String? = null,
    @SerialName("AlmDestino") val almDestino: String? = null,
    @SerialName("DesAlmDestino") val desAlmDestino: String? = null
)