package org.example.project.data.remote

import org.example.project.data.remote.response.IncidenciaPorFechaResponse
import org.example.project.domain.RepositoryIncidencias

class RepositoryImplIncidencias(val api: ApiServicesIncidencias) : RepositoryIncidencias {
    override suspend fun obtenerIncidencias(fecha: String): Result<List<IncidenciaPorFechaResponse>> {
        return try {
            val response = api.incidenciasPorFecha(fecha)
            Result.success(response)
        } catch (e: Exception) {
            // Aquí puedes mapear errores de Ktor a errores de tu app
            Result.failure(e)
        }
    }
}