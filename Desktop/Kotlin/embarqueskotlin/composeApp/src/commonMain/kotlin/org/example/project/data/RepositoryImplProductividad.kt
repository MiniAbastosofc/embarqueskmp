package org.example.project.data

import org.example.project.data.remote.ApiServicesProductividad
import org.example.project.domain.RepositoryProductividad
import org.example.project.domain.model.CajasProductividadModel
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

    override suspend fun obtenerRankingCajas(
        inicio: String,
        fin: String
    ): Result<List<CajasProductividadModel>> {
        return try {
            //TODO: Llamada al nuevo método del ApiService que creamos
            val response = api.getProductividadCajas(inicio, fin)

            if (response.success) {
                // Retornamos la lista de modelos (Checadores y Estibadores juntos)
                Result.success(response.data)
            } else {
                Result.failure(Exception("Error al obtener el ranking de cajas"))
            }
        } catch (e: Exception) {
            // Captura errores de red de Ktor o fallos de serialización
            Result.failure(e)
        }
    }

}