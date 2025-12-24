@file:OptIn(InternalAPI::class)

package org.example.project.ui.home.tabs.incidencias.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.util.collections.getValue
import io.ktor.utils.io.InternalAPI
import org.example.project.ui.home.tabs.incidencias.IncidenciasViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccionTomadaIncidencia() {
    val incidenciasViewModel = koinViewModel<IncidenciasViewModel>()
    val state by incidenciasViewModel.state.collectAsState()

    var texto by remember(state.accionTomadaTexto) {
        mutableStateOf(state.accionTomadaTexto)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 16.dp)
    ) {
        Text("Acción Tomada")
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = texto,
            onValueChange = {
                texto = it
                incidenciasViewModel.accionTomadaDescripcion(it)
            },
            label = { Text("Describe brevemente la solución tomada") },
            colors = TextFieldDefaults.colors(
                cursorColor = Color(0xFF1173D4),

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                focusedIndicatorColor = Color(0xFF1173D4),       // borde cuando está activo
                unfocusedIndicatorColor = Color(0xFFD3D9E1),     // borde cuando está inactivo

                focusedLabelColor = Color(0xFF1173D4)
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}