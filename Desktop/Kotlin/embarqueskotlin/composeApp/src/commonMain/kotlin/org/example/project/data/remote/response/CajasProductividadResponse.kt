package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import org.example.project.domain.model.CajasProductividadModel

@Serializable
data class CajasProductividadResponse(
    val success: Boolean,
    val data: List<CajasProductividadModel>
)