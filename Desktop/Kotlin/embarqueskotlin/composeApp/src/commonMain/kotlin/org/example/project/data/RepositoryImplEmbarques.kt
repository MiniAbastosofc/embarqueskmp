package org.example.project.data

import org.example.project.data.remote.ApiServicesEmbarques
import org.example.project.domain.RepositoryEmbarques
import org.example.project.domain.model.ObtenerEmbarquePorIdModel

class RespositoryImplEmbarques(val api: ApiServicesEmbarques) : RepositoryEmbarques {
    override suspend fun obtenerEmbarquePorIdRepository(id: Int): Result<ObtenerEmbarquePorIdModel?> {
        return try {
            val response = api.buscarEmbarquePorId(id)
            // Convertimos el Response de la capa de datos al Model de la capa de dominio
            Result.success(response.toDomain())
        } catch (e: Exception) {
            // Captura errores de red o parsing
            Result.failure(e)
        }
    }
}