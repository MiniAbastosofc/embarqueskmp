package org.example.project.ui.home.tabs.extension

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.UserDataManager
import org.example.project.domain.Repository

class ExtensionViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow(ExtensionState())
    val state: StateFlow<ExtensionState> = _state
    val user = UserDataManager.getCurrentUser()

    fun onChecadorChange(newChecador: String) {
        _state.update { it.copy(pruebaState = newChecador) }
    }

    fun onEstibadorChange(newEstibador: Int){
        _state.update {it.copy(estibadorState = newEstibador)}
    }
}