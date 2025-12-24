package org.example.project.ui.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.domain.ClearSessionUseCase
import org.example.project.domain.GetLoginStateUseCase

class SessionViewModel(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getLoginStateUseCase: GetLoginStateUseCase
) : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        checkSessionState()
    }

    private fun checkSessionState() {
        viewModelScope.launch {
            getLoginStateUseCase().collect { loggedIn ->
                isLoggedIn = loggedIn
                println("🔍 Estado de sesión actualizado: $loggedIn")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            println("🚪 Cerrando sesión...")
            clearSessionUseCase()
            isLoggedIn = false
            println("✅ Sesión cerrada")
        }
    }
}