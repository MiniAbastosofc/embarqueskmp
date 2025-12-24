package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.domain.model.DetalleEmbarqueFoto
import org.example.project.domain.model.DetalleEmbarqueInfo
import org.example.project.domain.model.FotoModel
import org.example.project.domain.model.RutaDetallesModel

@Serializable
data class RutaDetallesUnidoResponse(
    @SerialName("info") val info: RutaInfoResponse,
    @SerialName("fotos") val fotos: List<FotosRutasResponse>
)

// Extensión para mapear a dominio
fun RutaDetallesUnidoResponse.toDomain(): DetalleEmbarqueCompleto {

    val infoDomain = DetalleEmbarqueInfo(
        embarqueID = info.embarqueID,
        fechaEmbarque = info.fechaEmbarque,
        tipoEmbarque = info.tipoEmbarque,
        rutaID = info.rutaID,
        nombreRuta = info.nombreRuta,
        camionID = info.camionID,
        nombreCamion = info.nombreCamion,
        cantidadPedidos = info.cantidadPedidos,

        almOrigen = info.almOrigen,
        desAlmOrigen = info.desAlmOrigen,
        almDestino = info.almDestino,
        desAlmDestino = info.desAlmDestino,
        totalArticulos = info.totalArticulos,

        estadoID = info.estadoID,
        estado = info.estado,
        porcentajeAvance = info.porcentajeAvance,

        fechaInicio = info.fechaInicio,
        usuarioInicio = info.usuarioInicio,
        nombreUsuarioInicio = info.nombreUsuarioInicio,

        fechaCompletado = info.fechaCompletado,
        usuarioCompletado = info.usuarioCompletado,
        nombreUsuarioCompletado = info.nombreUsuarioCompletado,

        duracionMinutos = info.duracionMinutos,
        duracionFormateada = info.duracionFormateada,

        fechaCreacion = info.fechaCreacion,
        usuarioCreacion = info.usuarioCreacion
    )

    val fotosDomain = fotos.map {
        DetalleEmbarqueFoto(
            fotoID = it.fotoId,
            urlFoto = it.urlFoto,
            comentario = it.comentario,
            porcentajeAvance = it.porcentajeAvance,
            fechaCreacion = it.fechaCreacion,
            usuarioCreacion = it.usuarioCreacion,
            nombreUsuarioCreacion = it.nombreUsuarioCreacion
        )
    }

    return DetalleEmbarqueCompleto(
        info = infoDomain,
        fotos = fotosDomain
    )
}

fun FotosRutasResponse.toDomainFoto(): DetalleEmbarqueFoto {
    return DetalleEmbarqueFoto(
        fotoID = this.fotoId,
        urlFoto = this.urlFoto,
        comentario = this.comentario,
        porcentajeAvance = this.porcentajeAvance,
        fechaCreacion = this.fechaCreacion,
        usuarioCreacion = this.usuarioCreacion,
        nombreUsuarioCreacion = this.nombreUsuarioCreacion
    )
}