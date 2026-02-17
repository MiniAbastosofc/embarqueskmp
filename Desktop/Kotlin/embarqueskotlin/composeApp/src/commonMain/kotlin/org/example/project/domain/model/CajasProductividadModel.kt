package org.example.project.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class CajasProductividadModel(
    val posicion: String,
    val tipoPersonal: String, // "CHECADOR" o "ESTIBADOR"
    val nombre: String,
    val cajasTotales: Int,
    val horasTrabajadas: Double,
    val cajasPorHora: Double
)