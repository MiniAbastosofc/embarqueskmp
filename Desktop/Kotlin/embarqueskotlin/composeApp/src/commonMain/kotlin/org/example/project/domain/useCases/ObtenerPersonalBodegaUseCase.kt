package org.example.project.domain.useCases

import org.example.project.data.remote.response.PersonalBodegaLists
import org.example.project.domain.RepositoryPersonal

class ObtenerPersonalBodegaUseCase(
    private val repositoryPersonal: RepositoryPersonal
) {
    suspend operator fun invoke(): PersonalBodegaLists {
        return repositoryPersonal.estibadoresChecadores()
    }
}