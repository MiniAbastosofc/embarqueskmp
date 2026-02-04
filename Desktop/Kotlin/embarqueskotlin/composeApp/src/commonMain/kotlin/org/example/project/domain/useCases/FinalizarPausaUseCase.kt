package org.example.project.domain.useCases

import org.example.project.domain.RepositoryPausas

class FinalizarPausaUseCase(private val repository: RepositoryPausas) {
    suspend operator fun invoke(pausaId: Int, usuarioId: Int): Result<String> {
        return repository.finalizarPausaRepository(pausaId, usuarioId)
    }
}