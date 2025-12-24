package org.example.project.domain

// domain/usecase/AgregarFotoUseCase.kt
class AgregarFotoUseCase(
    private val repository: Repository
) {
    sealed class Result {
        object Loading : Result()
        data class Success(val message: String) : Result()
        data class Error(val message: String) : Result()
    }

    suspend operator fun invoke(
        embarqueID: Int,
        comentario: String?,
        porcentajeAvance: Int,
        usuarioID: Int?,
        filePath: String
    ): Result {
        return try {
            // Validaciones
            if (embarqueID <= 0) {
                return Result.Error("ID de embarque inválido")
            }

            if (usuarioID != null) {
                if (usuarioID <= 0) {
                    return Result.Error("ID de usuario inválido")
                }
            }

            if (porcentajeAvance !in 0..100) {
                return Result.Error("Porcentaje de avance debe estar entre 0 y 100")
            }

            if (filePath.isBlank()) {
                return Result.Error("Ruta de archivo inválida")
            }

            val message = repository.agregarFotoRepository(
                embarqueID = embarqueID,
                comentario = comentario,
                porcentajeAvance = porcentajeAvance,
                usuarioID = usuarioID,
                filePath = filePath
            )

            Result.Success(message)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error desconocido al subir foto")
        }
    }
}