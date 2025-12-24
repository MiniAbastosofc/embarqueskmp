package org.example.project.ui.home.tabs.incidencias.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.TipoIncidenciasModel
import org.example.project.ui.home.tabs.incidencias.IncidenciasViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TipoIncidencias() {
    val incidenciasViewModel = koinViewModel<IncidenciasViewModel>()
    val state by incidenciasViewModel.state.collectAsState()

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (state.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Error: ${state.error}")
        }
        return
    }

    val listaIncidencias = state.tiposDeIncidencias

    if (listaIncidencias != null) {
        if (listaIncidencias.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay tipos de incidencias disponibles")
            }
            return
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 16.dp)
    ) {
        Text("Tipo de Incidencia")

        Spacer(modifier = Modifier.height(10.dp))

        FlowRow {
            listaIncidencias?.forEach { item ->
                val isSelected = item.codigo == state.incidenciaSeleccionada

                TextButton(
                    onClick = {
                        incidenciasViewModel.tipoIncidenciaSeleccionada(item.codigo, item.id)
                    },
                    modifier = Modifier.padding(2.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (isSelected) Color(0xFF1173D4) else Color(0xFFD3D9E1)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = item.codigo,
                        color = if (isSelected) Color.White else Color(0xFF454D5D)
                    )
                }
            }
        }
    }
}