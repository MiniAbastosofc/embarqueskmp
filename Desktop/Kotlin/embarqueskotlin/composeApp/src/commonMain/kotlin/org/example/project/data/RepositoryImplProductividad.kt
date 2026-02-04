package org.example.project.data

import org.example.project.data.remote.ApiServicesProductividad
import org.example.project.domain.RepositoryProductividad
import org.example.project.domain.model.ProductividadModel

class RepositoryImplProductividad(
    val api: ApiServicesProductividad
) : RepositoryProductividad {
    override suspend fun obtenerProductividad(
        inicio: String,
        fin: String
    ): Result<List<ProductividadModel>> {
        return try {
            val response = api.getProductividadSemanal(inicio, fin)
            if (response.success) {
                Result.success(response.data)
            } else {
                Result.failure(Exception("Error al obtener productividad"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}