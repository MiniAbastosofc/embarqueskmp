package org.example.project.ui.home.tabs.pausas.Components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import org.example.project.ui.home.tabs.pausas.PausasViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ColumnScope.MotivoPausa(
    texto: String,
    onValueChange: (String) -> Unit
) {
//    var motivoPausaTexto by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .weight(1f)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
//            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("MOTIVO DE PAUSA")
        OutlinedTextField(
            value = texto,
            onValueChange = onValueChange,
            label = { Text("Detalle el motivo de la pausa, por favor") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            maxLines = 6,
            // Personalización de colores
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,   // Fondo cuando tiene el foco
                unfocusedContainerColor = Color.White, // Fondo cuando no tiene el foco
                focusedBorderColor = Color.LightGray,  // Color del borde al seleccionar
                unfocusedBorderColor = Color(0xFFD1D1D1), // Gris claro personalizado
                focusedLabelColor = Color.Gray,        // Color del texto de la etiqueta
                unfocusedLabelColor = Color.Gray
            )
        )
    }
}