package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import org.example.project.domain.model.CrearIncidenciaModel

@Serializable
class CrearIncidenciaResponse(
    val data: NuevaIncidenciaData
) {
    fun toDomain(): CrearIncidenciaModel {
        return CrearIncidenciaModel(
            NuevaIncidenciaId = data.NuevaIncidenciaID
        )
    }
}

@Serializable
data class NuevaIncidenciaData(
    val NuevaIncidenciaID: Int
)