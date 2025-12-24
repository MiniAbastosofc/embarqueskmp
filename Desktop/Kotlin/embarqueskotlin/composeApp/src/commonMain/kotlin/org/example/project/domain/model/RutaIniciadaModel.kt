package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RutaIniciadaModel(
    val EmbarqueID: Int,
    val UsuarioID: Int
)