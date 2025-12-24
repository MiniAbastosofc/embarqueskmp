package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.CompletarEmbarqueModel
import org.example.project.domain.model.EmbarqueCompletadoModel

@Serializable
data class CompletarEmbarqueResponse(
    @SerialName("message") val message: String,
    @SerialName("data") val data: EmbarqueCompletadoData
) {
    fun toDomain(): CompletarEmbarqueModel {
        return CompletarEmbarqueModel(
            message = this.message,
            data = this.data.toDomain()
        )
    }
}

@Serializable
data class EmbarqueCompletadoData(
    @SerialName("embarqueID") val embarqueID: String,
    @SerialName("fechaEmbarque") val fechaEmbarque: String,
    @SerialName("ruta") val ruta: String,
    @SerialName("descripcionRuta") val descripcionRuta: String,
    @SerialName("camion") val camion: String,
    @SerialName("descripcionCamion") val descripcionCamion: String,
    @SerialName("cantidadPedidos") val cantidadPedidos: Int,
    @SerialName("estadoID") val estadoID: Int,
    @SerialName("estado") val estado: String? = null,
    @SerialName("porcentajeAvance") val porcentajeAvance: Int? = null,
    @SerialName("fechaInicio") val fechaInicio: String? = null,
    @SerialName("fechaCompletado") val fechaCompletado: String? = null,
    @SerialName("minutosTranscurridos") val minutosTranscurridos: Int? = null,
    @SerialName("usuarioInicio") val usuarioInicio: String? = null,
    @SerialName("usuarioCompletado") val usuarioCompletado: String? = null
) {
    fun toDomain(): EmbarqueCompletadoModel {
        return EmbarqueCompletadoModel(
            embarqueID = this.embarqueID,
            fechaEmbarque = this.fechaEmbarque,
            ruta = this.ruta,
            descripcionRuta = this.descripcionRuta,
            camion = this.camion,
            descripcionCamion = this.descripcionCamion,
            cantidadPedidos = this.cantidadPedidos,
            estadoID = this.estadoID,
            estado = this.estado,
            porcentajeAvance = this.porcentajeAvance,
            fechaInicio = this.fechaInicio,
            fechaCompletado = this.fechaCompletado,
            minutosTranscurridos = this.minutosTranscurridos,
            usuarioInicio = this.usuarioInicio,
            usuarioCompletado = this.usuarioCompletado
        )
    }
}