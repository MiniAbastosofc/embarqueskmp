package org.example.project.domain

import org.example.project.domain.model.ObtenerEmbarquePorIdModel

interface RepositoryEmbarques {
    suspend fun obtenerEmbarquePorIdRepository(id: Int): Result<ObtenerEmbarquePorIdModel?>
}