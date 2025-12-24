package org.example.project.ui.home.tabs.incidencias.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.ui.home.tabs.incidencias.IncidenciasViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BotonEnviarIncidencias() {
    val incidenciasViewModel = koinViewModel<IncidenciasViewModel>()
    val state by incidenciasViewModel.state.collectAsState()

    val isEnabled =
        state.incidenciaSeleccionada.isNotEmpty() && state.descripcionIncidencia.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = if (isEnabled) Color(0xFF1173D4) else Color(0xFFCCCCCC))
            .clickable(onClick = { incidenciasViewModel.enviarIncidencia() }, enabled = isEnabled)
            .padding(10.dp),
    ) {
        Text(
            text = "Enviar Reporte",
            color = if (isEnabled) Color.White else Color(0xFF888888),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )
    }
}