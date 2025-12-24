package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import org.example.project.domain.model.RutaDto
import org.example.project.domain.model.RutasModel

@Serializable
data class RutasResponseDto(
    val success: Boolean,
    val data: List<RutaDto>,
    val total: Int,
    val fecha: String
){
    fun toDomain(): List<RutasModel> {
        return data.map { it.toDomain() }
    }
}