package org.example.project.domain.model

data class RutaDetallesModel(
    val id: Int,
    val routeName: String,
    val status: String,
    val percentage: Int,
    val durationFormatted: String,
    val photoCount: Int,
    val embarqueId: Int,
    val fechaEmbarque: String,

    // Estos deben ser NULLABLES
    val ruta: String? = null,
    val descripcionRuta: String? = null,
    val camion: String? = null,
    val descripcionCamion: String? = null,

    // Nuevos datos de Sucursal
    val almOrigen: String? = null,
    val desAlmOrigen: String? = null,
    val almDestino: String? = null,
    val desAlmDestino: String? = null,

    val cantidadPedidos: Int,
    val estadoID: Int,
    val estado: String,
    val fechaInicio: String?,
    val fechaCompletado: String?,
    val usuarioInicio: String?,
    val usuarioCompletado: String?,
    val nombreUsuarioInicio: String?,
    val nombreUsuarioCompletado: String? = null,
    val duracionMinutos: Int,
    val fechaCreacion: String,
    val usuarioCreacion: String,
    val fotos: List<DetalleEmbarqueCompleto>
)

data class FotoModel(
    val fotoID: Int,
    val urlFoto: String,
    val comentario: String? = null,
    val porcentajeAvance: Int,
    val fechaCreacion: String,
    val usuarioCreacion: String,
    val nombreUsuarioCreacion: String
)