package org.example.project.domain

import org.example.project.data.remote.response.IncidenciaPorFechaResponse

interface RepositoryIncidencias {
    suspend fun obtenerIncidencias(fecha: String): Result<List<IncidenciaPorFechaResponse>>
}