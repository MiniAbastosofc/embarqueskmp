package org.example.project.domain

import org.example.project.domain.model.CompletarEmbarqueModel

class CompletarEmbarqueUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        embarqueID: String?,
        usuarioID: Int?
    ): Result<CompletarEmbarqueModel> {
        return try {
            // Validaciones corregidas
            if (embarqueID.isNullOrEmpty() || embarqueID.toIntOrNull() == null) {
                return Result.failure(IllegalArgumentException("ID de embarque inválido"))
            }
            if (usuarioID == null || usuarioID <= 0) {
                return Result.failure(IllegalArgumentException("ID de usuario inválido"))
            }

            val result = repository.completarEmbarqueRepository(embarqueID, usuarioID)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}