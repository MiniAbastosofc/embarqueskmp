package org.example.project.ui.home.tabs.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.UserDataManager
import org.example.project.domain.GetLoginStateUseCase
import org.example.project.domain.LoginUseCase
import org.example.project.domain.Repository
import org.example.project.domain.SaveSessionUseCase
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class LoginViewModel(
    val loginUseCase: LoginUseCase,
    private val repository: Repository,
    private val saveSessionUseCase: SaveSessionUseCase, // Nuevo
    private val getLoginStateUseCase: GetLoginStateUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state


    fun onUsernameChange(newUsername: String) {
        _state.update { it.copy(usuarioText = newUsername) }
    }

    fun onPasswordChange(newPassword: String) {
        _state.update { it.copy(passwordText = newPassword) }
    }

    fun onLoginClicked() {
        // 1. Obtener los datos actuales del estado
        val currentState = state.value
        val usuario = currentState.usuarioText
        val password = currentState.passwordText

        // 2. Iniciar una corutina usando el scope del ViewModel
        viewModelScope.launch {

            // 3. Actualizar el estado a "Cargando"
            // ¿Por qué?: Para que la UI (ej. un ProgressBar) se muestre al usuario.
            _state.update {
                it.copy(isLoading = true, error = null) // Limpiamos errores previos
            }

            try {
                // 4. Llamar al Caso de Uso (Lógica de Negocio/API Call)
                // ¿Por qué?: Delegamos la tarea a la capa de dominio. Esto es una función suspendida.
                val userModel = loginUseCase.invoke(usuario, password)
                saveSessionUseCase(
                    token = "authenticated_$usuario", // Solo el usuario
                    userId = userModel.UsuarioId, // ✅ ID REAL del usuario
                    userName = userModel.Usuario // El nombre de usuario del login
                )
                UserDataManager.saveUserData(userModel)
                // 5. Si tiene éxito: Actualizar el estado a "Autenticado"
                // ¿Por qué?: La UI sabrá que el login fue exitoso y puede navegar a la pantalla principal.
                _state.update {
                    it.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        // loginTexts = userModel // Puedes guardar el usuario si lo necesitas más tarde
                    )
                }
            } catch (e: IllegalArgumentException) {
                // 6. Manejar errores específicos de validación (ej. campos vacíos)
                _state.update {
                    it.copy(isLoading = false, error = e.message)
                }

            } catch (e: Exception) {
                // 7. Manejar otros errores (ej. red, credenciales incorrectas, API caída)
                // ¿Por qué?: Capturamos cualquier fallo y lo mostramos como un mensaje de error en la UI.
                _state.update {
                    it.copy(isLoading = false, error = e.message ?: "Ocurrió un error inesperado")
                }
            }
        }
    }
}