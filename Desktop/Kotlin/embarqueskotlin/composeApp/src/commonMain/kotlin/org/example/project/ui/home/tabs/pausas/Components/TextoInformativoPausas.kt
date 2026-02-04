package org.example.project.ui.home.tabs.pausas.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.project.ui.home.tabs.admin.AdminViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TextoInformativoPausas() {
    val adminViewModel = koinViewModel<AdminViewModel>()

    Column {
        Text("Detener proceso", fontSize = 29.sp, fontWeight = FontWeight.Bold)
        Text(
            "Seleccione el tipo de pausa e ingrese el motivo para pausar el embarque actual",
            color = Color(0xFFD1D1D1)
        )
    }
}