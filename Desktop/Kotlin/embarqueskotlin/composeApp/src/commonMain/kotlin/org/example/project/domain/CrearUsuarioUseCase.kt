package org.example.project.domain

import org.example.project.data.remote.CrearUsuarioRequest
import org.example.project.data.remote.response.CrearUsuarioResponse
import org.example.project.domain.model.CrearUsuarioModel

class CrearUsuarioUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(request: CrearUsuarioRequest): Result<CrearUsuarioModel> {
        return repository.crearUsuario(request)
    }
}