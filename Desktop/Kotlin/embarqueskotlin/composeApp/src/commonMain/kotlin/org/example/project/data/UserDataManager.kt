package org.example.project.data

import org.example.project.domain.model.LoginModel

object UserDataManager {
    private var currentUser: LoginModel? = null

    fun saveUserData(user: LoginModel) {
        currentUser = user
    }

    fun getCurrentUser(): LoginModel? = currentUser
    fun getUserId(): Int = currentUser?.UsuarioId ?: -1
    fun getUserRole(): Int = currentUser?.RolID ?: -1
    fun getUserName(): String = currentUser?.Usuario ?: ""
    fun getFullName(): String = currentUser?.let { "${it.Nombre} ${it.Apellido}" } ?: ""
    fun clearUserData() {
        currentUser = null
    }
}