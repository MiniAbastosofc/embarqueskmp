package org.example.project.domain.model

data class DetalleEmbarqueInfo(
    val embarqueID: Int,
    val fechaEmbarque: String,
    val tipoEmbarque: String,
    val rutaID: String?,
    val nombreRuta: String?,
    val camionID: String?,
    val nombreCamion: String?,
    val cantidadPedidos: Int,

    val almOrigen: String?,
    val desAlmOrigen: String?,
    val almDestino: String?,
    val desAlmDestino: String?,
    val totalArticulos: Int?,

    val estadoID: Int,
    val estado: String,
    val porcentajeAvance: Int,

    val fechaInicio: String?,
    val usuarioInicio: String?,
    val nombreUsuarioInicio: String?,

    val fechaCompletado: String?,
    val usuarioCompletado: String?,
    val nombreUsuarioCompletado: String?,

    val duracionMinutos: Int?,
    val duracionFormateada: String,

    val fechaCreacion: String,
    val usuarioCreacion: String
)
data class DetalleEmbarqueCompleto(
    val info: DetalleEmbarqueInfo,
    val fotos: List<DetalleEmbarqueFoto>
)

data class DetalleEmbarqueFoto(
    val fotoID: Int,
    val urlFoto: String,
    val comentario: String?,
    val porcentajeAvance: Int,
    val fechaCreacion: String,
    val usuarioCreacion: String,
    val nombreUsuarioCreacion: String?
)