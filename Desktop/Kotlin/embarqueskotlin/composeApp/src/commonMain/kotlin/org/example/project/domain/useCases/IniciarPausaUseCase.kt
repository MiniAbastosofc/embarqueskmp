package org.example.project.domain.useCases

import org.example.project.data.remote.request.IniciarPausaRequest
import org.example.project.domain.RepositoryPausas
import org.example.project.domain.model.IniciarPausaModel

class IniciarPausaUseCase(
    private val repository: RepositoryPausas
) {
    suspend operator fun invoke(request: IniciarPausaRequest): Result<IniciarPausaModel> {
        return repository.iniciarPausaRepository(request)
    }
}