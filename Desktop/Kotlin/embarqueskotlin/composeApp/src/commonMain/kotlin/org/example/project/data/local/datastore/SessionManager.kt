package org.example.project.data.local.datastore

import kotlinx.coroutines.flow.Flow
import org.example.project.domain.model.LoginModel

interface SessionRepository {
    suspend fun saveSession(token: String, userId: Int, userName: String)
    suspend fun clearSession()
    fun getAuthToken(): Flow<String?>
    fun getLoginState(): Flow<Boolean>
    fun getUserId(): Flow<Int?>
    fun getUserName(): Flow<String?>

    suspend fun saveUserData(loginModel: LoginModel)
    fun getUserRole(): Flow<Int?>
    fun getUserData(): Flow<LoginModel?>
    fun getCurrentUser(): Flow<LoginModel?>
    fun getUserIdStream(): Flow<Int?>
    fun getUserRoleStream(): Flow<Int?>
}