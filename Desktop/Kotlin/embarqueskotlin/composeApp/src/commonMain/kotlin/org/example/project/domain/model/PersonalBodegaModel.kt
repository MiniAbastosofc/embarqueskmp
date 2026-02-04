package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonalBodegaModel(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val activo: Boolean,
    val rol: Int
)