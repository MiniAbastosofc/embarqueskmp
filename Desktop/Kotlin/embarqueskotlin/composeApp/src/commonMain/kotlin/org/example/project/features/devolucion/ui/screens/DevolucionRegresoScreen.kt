package org.example.project.features.devolucion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.features.devolucion.ui.components.BuscadorDevolucionRegreso
import org.example.project.features.devolucion.ui.components.CardDevolucion
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DevolucionRegresoScreen(navController: NavHostController) {
    val devolucionViewModel = koinViewModel<DevolucionViewModel>()
    val state by devolucionViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp)
    ) {
        BuscadorDevolucionRegreso(
            texto = "Prueba",
            onValueChange = { nuevoTexto ->
                devolucionViewModel.onBuscarFolioDevolucion(nuevoTexto)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CardDevolucion(navController = navController)
    }
}