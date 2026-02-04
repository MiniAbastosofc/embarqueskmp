@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.project.ui.home.tabs.incidencias

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.incidencias.components.AccionTomadaIncidencia
import org.example.project.ui.home.tabs.incidencias.components.AdjuntarEvidenciaIncidencia
import org.example.project.ui.home.tabs.incidencias.components.BotonEnviarIncidencias
import org.example.project.ui.home.tabs.incidencias.components.DescripcionIncidencias
import org.example.project.ui.home.tabs.incidencias.components.TipoIncidencias
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IncidenciasScreen(embarqueId: String?, navController: NavHostController, imagenUri: String?) {
    val incidenciasViewModel = koinViewModel<IncidenciasViewModel>()
    val state by incidenciasViewModel.state.collectAsState()

    LaunchedEffect(imagenUri) {
        println("LOG: Recibí imagenUri de la cámara: $imagenUri")
        incidenciasViewModel.setEvidenciaUri(imagenUri)
    }
    LaunchedEffect(Unit) {
        println("DEBUG NAVEGACIÓN: El path recibido es -> $imagenUri")
    }
    LaunchedEffect(imagenUri) {
        incidenciasViewModel.setEvidenciaUri(imagenUri)
    }

    LaunchedEffect(state.operacionExitosa) {
        if (state.operacionExitosa) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(embarqueId) {
        if (embarqueId != null) {
            incidenciasViewModel.setEmbarqueId(embarqueId)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color(0xFF64B5F6)
                )
            }
            Text(
                "Reportar Incidencia",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
        TipoIncidencias()
        Spacer(modifier = Modifier.height(10.dp))
        DescripcionIncidencias()
        Spacer(modifier = Modifier.height(10.dp))
        AdjuntarEvidenciaIncidencia(imagenUri = imagenUri)
        Spacer(modifier = Modifier.height(10.dp))
        AccionTomadaIncidencia()
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(10.dp))
        BotonEnviarIncidencias(imagenUri = imagenUri)
    }
}