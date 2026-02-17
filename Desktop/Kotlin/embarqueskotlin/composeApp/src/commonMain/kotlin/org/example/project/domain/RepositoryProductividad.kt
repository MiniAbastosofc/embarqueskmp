package org.example.project.domain

import org.example.project.domain.model.CajasProductividadModel
import org.example.project.domain.model.ProductividadModel

interface RepositoryProductividad {
    suspend fun obtenerProductividad(inicio: String, fin: String): Result<List<ProductividadModel>>
    suspend fun obtenerRankingCajas(
        inicio: String,
        fin: String
    ): Result<List<CajasProductividadModel>>
}