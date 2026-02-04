package org.example.project.domain

import org.example.project.domain.model.ProductividadModel

interface RepositoryProductividad {
    suspend fun obtenerProductividad(inicio: String, fin: String): Result<List<ProductividadModel>>
}