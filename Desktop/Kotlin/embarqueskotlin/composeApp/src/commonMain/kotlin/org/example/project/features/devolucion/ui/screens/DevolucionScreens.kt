package org.example.project.features.devolucion.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.example.project.features.devolucion.ui.components.BuscadorDevolucion
import org.example.project.features.devolucion.ui.components.CardsDevolucion
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DevolucionScreen(navController: NavHostController) {
    val devolucionViewModel = koinViewModel<DevolucionViewModel>()
    val state by devolucionViewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        BuscadorDevolucion(
            texto = state.prueba,
            onValueChange = { devolucionViewModel.onMotivoTexto(it) })
        Box(modifier = Modifier.weight(1f)) {
            CardsDevolucion(navController = navController)
        }
    }
}