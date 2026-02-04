package org.example.project.domain

class IniciarRutaUseCase(private val repository: Repository) {
    // Este caso de uso solo necesita el EmbarqueID y el UsuarioID para funcionar
    suspend operator fun invoke(
        embarqueId: Int,
        usuarioId: Int?,
        tonelaje: Double,
        checadorId: Int,
        estibadorId: Int
    ): String {
        // La lógica de negocio está aquí (ej: validaciones antes de llamar al repo)
        if (usuarioId != null) {
            if (embarqueId <= 0 || usuarioId <= 0) {
                throw IllegalArgumentException("IDs de embarque o usuario inválidos.")
            }
        }
        return repository.iniciarRutasRepository(embarqueId, usuarioId, tonelaje, checadorId, estibadorId)
    }
}