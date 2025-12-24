package org.example.project.ui.home.tabs.login

import org.example.project.domain.model.LoginModel

data class LoginState(
//    val loginTexts: LoginModel? = null
    val usuarioText: String = "",
    val passwordText: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val userData: LoginModel? = null
)