package org.example.project.domain.useCases

import org.example.project.domain.RepositoryProductividad
import org.example.project.domain.model.ProductividadModel

class GetProductividadUseCase(private val repository: RepositoryProductividad) {
    suspend operator fun invoke(inicio: String, fin: String): Result<List<ProductividadModel>> {
        // Aquí podrías agregar validaciones de fechas si fuera necesario
        return repository.obtenerProductividad(inicio, fin)
    }
}