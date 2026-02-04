package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductividadModel(
    val tipoPersonal: String,
    val nombre: String,
    val tonelaje: Double,
    val horasTrabajadas: Double,
    val eficiencia: Double
)

@Serializable
data class ProductividadResponse(
    val success: Boolean,
    val data: List<ProductividadModel>
)
