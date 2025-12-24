package org.example.project.domain

import org.example.project.domain.model.RutasModel

class GetRutasUseCase(private val repository: Repository) {
    suspend operator fun invoke(fecha: String): List<RutasModel> {
        if (fecha.isBlank()) {
            throw IllegalArgumentException("La fecha es requerida para buscar embarques.")
        }
        return repository.getRutasRepository(fecha)
    }
}