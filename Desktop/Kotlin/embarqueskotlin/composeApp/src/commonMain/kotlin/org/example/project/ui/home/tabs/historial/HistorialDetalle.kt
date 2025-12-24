package org.example.project.ui.home.tabs.historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.historial.components.HistorialDetalleCard
import org.example.project.ui.home.tabs.historial.components.HistorialFotosCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistorialDetalle(navController: NavHostController, id: Int?) {
    val historialViewModel = koinViewModel<HistorialViewModel>()
    val state by historialViewModel.state.collectAsState()

    LaunchedEffect(key1 = id) {
        if (id != null) {
            historialViewModel.loadShipmentDetails(id)
        }
    }

    // Manejar estado de carga y error
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color(0xFF64B5F6))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Cargando detalles...")
        }
        return
    }

    state.error?.let { error ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error: $error", color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Regresar")
            }
        }
        return
    }

    // Si no hay detalles, mostrar mensaje
    if (state.details == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No se encontraron detalles")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Regresar")
            }
        }
        return
    }

    // UI principal con datos
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Header mejorado ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
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
                "Detalles",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Pasar los detalles a la card
        HistorialDetalleCard(details = state.details!!)

        Spacer(modifier = Modifier.height(10.dp))

        Text("Fotos Capturadas", fontWeight = FontWeight.SemiBold, fontSize = 10.sp)

        // Pasar las fotos reales al LazyColumn
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//            items(state.details!!.fotos) { foto ->
            items(state.details?.fotos ?: emptyList()) { foto ->
                HistorialFotosCard(foto = foto)
            }
        }
    }
}