package org.example.project.domain.useCases

import org.example.project.domain.RepositoryProductividad
import org.example.project.domain.model.CajasProductividadModel

class GetRankingCajasUseCase(private val repository: RepositoryProductividad) {

    suspend operator fun invoke(
        inicio: String,
        fin: String
    ): Result<List<CajasProductividadModel>> {

        if (inicio.isBlank() || fin.isBlank()) {
            return Result.failure(Exception("Las fechas de inicio y fin son obligatorias."))
        }
        val dateRegex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!dateRegex.matches(inicio) || !dateRegex.matches(fin)) {
            return Result.failure(Exception("El formato de fecha debe ser YYYY-MM-DD."))
        }
        return repository.obtenerRankingCajas(inicio, fin)
    }
}