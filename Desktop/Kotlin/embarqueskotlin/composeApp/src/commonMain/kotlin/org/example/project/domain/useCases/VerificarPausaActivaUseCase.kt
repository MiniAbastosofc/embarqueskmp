package org.example.project.domain.useCases

import org.example.project.data.remote.response.VerificarPausaData
import org.example.project.domain.RepositoryPausas

class VerificarPausaActivaUseCase(
    private val repository: RepositoryPausas
) {
    suspend operator fun invoke(embarqueId: Int): Result<VerificarPausaData> {
        return repository.verificarPausaActiva(embarqueId)
    }
}