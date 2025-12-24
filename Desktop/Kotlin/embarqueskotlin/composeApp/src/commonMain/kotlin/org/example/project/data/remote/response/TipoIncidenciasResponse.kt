package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import org.example.project.domain.model.TipoIncidenciasModel

@Serializable
data class TipoIncidenciasResponse(
    val id: Int,
    val codigo: String,
    val descripcion: String,
    val activo: Boolean
) {
    fun toDomain(): TipoIncidenciasModel {
        return TipoIncidenciasModel(
            id = id,
            codigo = codigo,
            descripcion = descripcion,
            activo = activo
        )
    }
}