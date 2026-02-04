package org.example.project.domain.useCases

import org.example.project.data.remote.response.IncidenciaPorFechaResponse
import org.example.project.domain.RepositoryIncidencias

class GetIncidenciasUseCase(private val repository: RepositoryIncidencias) {

    suspend operator fun invoke(fecha: String): Result<List<IncidenciaPorFechaResponse>> {
        // Validación opcional: verificar que la fecha no esté vacía antes de llamar al repo
        if (fecha.isBlank()) {
            return Result.failure(Exception("La fecha no puede estar vacía"))
        }

        return repository.obtenerIncidencias(fecha)
    }
}