package org.example.project.domain

import kotlinx.coroutines.flow.Flow
import org.example.project.data.local.datastore.SessionRepository
import org.example.project.domain.model.LoginModel

class GetLoginStateUseCase(private val sessionRepository: SessionRepository) {
    operator fun invoke(): Flow<Boolean> = sessionRepository.getLoginState()
}

class SaveSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(token: String, userId: Int, userName: String) {
        sessionRepository.saveSession(token, userId, userName)
    }
}

class ClearSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke() {
        sessionRepository.clearSession()
    }
}

class GetAuthTokenUseCase(private val sessionRepository: SessionRepository) {
    operator fun invoke(): Flow<String?> = sessionRepository.getAuthToken()
}