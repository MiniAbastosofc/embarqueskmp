package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.domain.model.DetalleEmbarqueInfo
import org.example.project.domain.model.RutaDetallesModel

@Serializable
data class RutaDetallesFechaResponse(
    @SerialName("embarqueID") val embarqueID: Int,
    @SerialName("fechaEmbarque") val fechaEmbarque: String,
    @SerialName("tipoEmbarque") val tipoEmbarque: String,

    @SerialName("ruta") val ruta: String? = null,
    @SerialName("descripcionRuta") val descripcionRuta: String? = null,
    @SerialName("camion") val camion: String? = null,
    @SerialName("descripcionCamion") val descripcionCamion: String? = null,

    @SerialName("almOrigen") val almOrigen: String? = null,
    @SerialName("desAlmOrigen") val desAlmOrigen: String? = null,
    @SerialName("almDestino") val almDestino: String? = null,
    @SerialName("desAlmDestino") val desAlmDestino: String? = null,
    @SerialName("totalArticulos") val totalArticulos: Int? = null,

    @SerialName("cantidadPedidos") val cantidadPedidos: Int,
    @SerialName("estadoID") val estadoID: Int,
    @SerialName("porcentajeAvance") val porcentajeAvance: Int,
    @SerialName("estado") val estado: String? = null,
    @SerialName("totalFotos") val totalFotos: Int? = null,
    @SerialName("fechaInicio") val fechaInicio: String? = null,
    @SerialName("fechaCompletado") val fechaCompletado: String? = null,
    @SerialName("usuarioInicio") val usuarioInicio: String? = null,
    @SerialName("usuarioCompletado") val usuarioCompletado: String? = null,
    @SerialName("nombreUsuarioInicio") val nombreUsuarioInicio: String? = null,
    @SerialName("nombreUsuarioCompletado") val nombreUsuarioCompletado: String? = null,
    @SerialName("duracionMinutos") val duracionMinutos: Int? = null,
    @SerialName("duracionFormateada") val duracionFormateada: String? = null,
    @SerialName("fechaCreacion") val fechaCreacion: String? = null,
    @SerialName("usuarioCreacion") val usuarioCreacion: String? = null,

    @SerialName("fotos") val fotos: List<FotosRutasResponse>? = emptyList()
)

fun RutaDetallesFechaResponse.toDomainDetalles(): DetalleEmbarqueCompleto {

    val info = DetalleEmbarqueInfo(
        embarqueID = embarqueID,
        fechaEmbarque = fechaEmbarque,
        tipoEmbarque = tipoEmbarque,
        rutaID = ruta,                     // mapear a rutaID
        nombreRuta = descripcionRuta,      // mapear a nombreRuta
        camionID = camion,                 // mapear a camionID
        nombreCamion = descripcionCamion,  // mapear a nombreCamion
        cantidadPedidos = cantidadPedidos,

        almOrigen = almOrigen,
        desAlmOrigen = desAlmOrigen,
        almDestino = almDestino,
        desAlmDestino = desAlmDestino,
        totalArticulos = totalArticulos,

        estadoID = estadoID,
        estado = estado ?: "N/A",
        porcentajeAvance = porcentajeAvance,

        fechaInicio = fechaInicio,
        usuarioInicio = usuarioInicio,
        nombreUsuarioInicio = nombreUsuarioInicio,

        fechaCompletado = fechaCompletado,
        usuarioCompletado = usuarioCompletado,
        nombreUsuarioCompletado = nombreUsuarioCompletado,

        duracionMinutos = duracionMinutos ?: 0,
        duracionFormateada = duracionFormateada ?: "N/A",
        fechaCreacion = fechaCreacion ?: "N/A",
        usuarioCreacion = usuarioCreacion ?: "N/A"
    )

    val fotosList = fotos?.map { it.toDomainFoto() } ?: emptyList()

    return DetalleEmbarqueCompleto(
        info = info,
        fotos = fotosList
    )
}