package org.example.project.data

import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.datastore.PreferencesDataSource
import org.example.project.data.local.datastore.SessionRepository
import org.example.project.domain.model.LoginModel

class SessionRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
) : SessionRepository {

    override suspend fun saveSession(token: String, userId: Int, userName: String) {
        preferencesDataSource.saveAuthToken(token)
        preferencesDataSource.saveLoginState(true)
        preferencesDataSource.saveUserId(userId)
        preferencesDataSource.saveUserName(userName)
    }

    override suspend fun clearSession() {
        preferencesDataSource.clearSession()
    }

    override fun getAuthToken(): Flow<String?> {
        return preferencesDataSource.getAuthToken()
    }

    override fun getLoginState(): Flow<Boolean> {
        return preferencesDataSource.getLoginState()
    }

    override fun getUserId(): Flow<Int?> {
        return preferencesDataSource.getUserId()
    }

    override fun getUserName(): Flow<String?> {
        return preferencesDataSource.getUserName()
    }

    override suspend fun saveUserData(loginModel: LoginModel) {
        preferencesDataSource.saveUserData(loginModel)
    }

    override fun getUserData(): Flow<LoginModel?> {
        return preferencesDataSource.getUserData()
    }

    override fun getUserRole(): Flow<Int?> {
        return preferencesDataSource.getUserRole()
    }

    fun getUserFullName(): Flow<String?> {
        return preferencesDataSource.getUserFullName()
    }

    fun getEmployeeNumber(): Flow<String?> {
        return preferencesDataSource.getEmployeeNumber()
    }

    override fun getCurrentUser(): Flow<LoginModel?> {
        return preferencesDataSource.getUserData() // O getCurrentUser() si lo tienes
    }

    override fun getUserIdStream(): Flow<Int?> {
        return preferencesDataSource.getUserId()
    }

    override fun getUserRoleStream(): Flow<Int?> {
        return preferencesDataSource.getUserRole()
    }
}