package org.example.project.domain

import org.example.project.domain.model.LoginModel

class LoginUseCase(private val repository: Repository) {
    suspend operator fun invoke(usuario: String, passwordPlain: String): LoginModel {

        val trimmedUsuario = usuario.trim()
        val trimmedPassword = passwordPlain.trim()

        if (trimmedUsuario.isBlank() || trimmedPassword.isBlank()) {
            throw IllegalArgumentException("usuario and password are required")
        }
        return repository.loginAuth(trimmedUsuario, trimmedPassword)
    }
}