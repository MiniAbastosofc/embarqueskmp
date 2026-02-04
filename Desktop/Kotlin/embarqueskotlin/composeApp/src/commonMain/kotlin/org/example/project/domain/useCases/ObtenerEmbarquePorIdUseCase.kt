package org.example.project.domain.useCases

import org.example.project.domain.RepositoryEmbarques
import org.example.project.domain.model.ObtenerEmbarquePorIdModel

class BuscarEmbarqueUseCase(
    private val repository: RepositoryEmbarques // La interfaz
) {
    suspend operator fun invoke(id: Int): Result<ObtenerEmbarquePorIdModel?> {
        // Aquí podrías agregar lógica de negocio adicional si fuera necesaria
        return repository.obtenerEmbarquePorIdRepository(id)
    }
}