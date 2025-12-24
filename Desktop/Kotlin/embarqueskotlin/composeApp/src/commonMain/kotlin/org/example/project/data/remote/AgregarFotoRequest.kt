package org.example.project.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgregarFotoRequest(
    @SerialName("embarqueID") val embarqueID: Int,
    @SerialName("comentario") val comentario: String? = null,
    @SerialName("porcentajeAvance") val porcentajeAvance: Int,
    @SerialName("usuarioID") val usuarioID: Int,
    @SerialName("file") val file: FileData? = null
)

@Serializable
data class FileData(
    @SerialName("filename") val filename: String,
    @SerialName("path") val path: String,
    @SerialName("mimetype") val mimetype: String
)