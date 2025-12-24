package org.example.project.domain

import org.example.project.data.remote.request.CrearIncidenciaRequest
import org.example.project.domain.model.CrearIncidenciaModel

class CrearIncidenciaUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        request: CrearIncidenciaRequest
    ): CrearIncidenciaModel {
        return repository.crearincidenciaApiService(request)
    }
}