package org.example.project.ui.home.tabs.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.datastore.SessionRepository
import org.example.project.domain.ClearSessionUseCase
import org.example.project.domain.GetLoginStateUseCase
import org.example.project.domain.model.LoginModel

// presentation/user/UserViewModel.kt
class UserViewModel(
    private val sessionRepository: SessionRepository, // ✅ Usar el repository directamente
    private val getLoginStateUseCase: GetLoginStateUseCase
) : ViewModel() {

    private val _currentUser = MutableStateFlow<LoginModel?>(null)
    val currentUser: StateFlow<LoginModel?> = _currentUser.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _userRole = MutableStateFlow(-1)
    val userRole: StateFlow<Int> = _userRole.asStateFlow()

    init {
        loadUserData()
        checkLoginState()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            // ✅ Usar directamente el repository
            sessionRepository.getUserData().collect { user ->
                _currentUser.value = user
                _userId.value = user?.UsuarioId ?: -1
                _userRole.value = user?.RolID ?: -1
            }
        }
    }

    private fun checkLoginState() {
        viewModelScope.launch {
            getLoginStateUseCase().collect { loggedIn ->
                _isLoggedIn.value = loggedIn
            }
        }
    }

    fun refreshUserData() {
        loadUserData()
    }

    fun logout() {
        viewModelScope.launch {
            // ✅ Usar directamente el repository
            sessionRepository.clearSession()
            _currentUser.value = null
            _isLoggedIn.value = false
            _userId.value = -1
            _userRole.value = -1
        }
    }

    // Métodos de conveniencia
    fun getUserName(): String = _currentUser.value?.Usuario ?: ""
    fun getFullName(): String = _currentUser.value?.let {
        "${it.Nombre} ${it.Apellido}"
    } ?: ""

    fun getEmployeeNumber(): String = _currentUser.value?.NumeroEmpleado ?: ""
    fun getRoleName(): String = _currentUser.value?.NombreRol ?: ""

    fun isAdmin(): Boolean = _userRole.value == 1
    fun hasRole(roleId: Int): Boolean = _userRole.value == roleId
}