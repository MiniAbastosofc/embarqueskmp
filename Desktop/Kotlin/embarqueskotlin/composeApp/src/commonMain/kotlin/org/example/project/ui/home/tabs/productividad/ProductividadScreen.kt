package org.example.project.ui.home.tabs.productividad

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.productividad.components.ProductividadItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductividadScreen(navController: NavHostController) {
    val viewModel = koinViewModel<ProductividadViewModel>()
    val state by viewModel.state.collectAsState()

    var tabSeleccionada by remember { mutableStateOf(0) }
    var showInicioPicker by remember { mutableStateOf(false) }
    var showFinPicker by remember { mutableStateOf(false) }

    // --- LÓGICA DE CARGA AUTOMÁTICA ---
    // Cuando ambas fechas cambian y no están vacías, se dispara la carga
    LaunchedEffect(state.fechaInicio, state.fechaFin) {
        if (state.fechaInicio.isNotEmpty() && state.fechaFin.isNotEmpty()) {
            viewModel.cargarReporte(state.fechaInicio, state.fechaFin)
        }
    }

    // --- COMPONENTES DEL CALENDARIO (Invisibles hasta que show sea true) ---
    PlatformDatePicker(
        show = showInicioPicker,
        onDismiss = { showInicioPicker = false },
        onDateSelected = { viewModel.onFechaInicioChanged(it) }
    )

    PlatformDatePicker(
        show = showFinPicker,
        onDismiss = { showFinPicker = false },
        onDateSelected = { viewModel.onFechaFinChanged(it) }
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Productividad Semanal",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        // --- SELECTORES DE FECHA ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Botón Inicio
            Button(
                onClick = { showInicioPicker = true },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (state.fechaInicio.isEmpty()) "Inicio" else "Desde: ${state.fechaInicio}")
            }

            // Botón Fin
            Button(
                onClick = { showFinPicker = true },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (state.fechaFin.isEmpty()) "Fin" else "Hasta: ${state.fechaFin}")
            }
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                if (state.fechaInicio.isNotEmpty() && state.fechaFin.isNotEmpty()) {
                    viewModel.cargarReporte(state.fechaInicio, state.fechaFin)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Consultar Productividad")
        }

        Spacer(Modifier.height(16.dp))

        // --- TABS ---
        TabRow(selectedTabIndex = tabSeleccionada) {
            Tab(
                selected = tabSeleccionada == 0,
                onClick = { tabSeleccionada = 0 },
                text = { Text("Estibadores (${state.estibadores.size})") }
            )
            Tab(
                selected = tabSeleccionada == 1,
                onClick = { tabSeleccionada = 1 },
                text = { Text("Checadores (${state.checadores.size})") }
            )
        }

        // --- LISTA DE RESULTADOS ---
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.fechaInicio.isEmpty() || state.fechaFin.isEmpty()) {
            // Mensaje informativo cuando no hay fechas seleccionadas
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Seleccione un rango de fechas", color = Color.Gray)
            }
        } else {
            val listaAMostrar = if (tabSeleccionada == 0) state.estibadores else state.checadores

            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
                itemsIndexed(listaAMostrar) { index, persona ->
                    ProductividadItem(item = persona, posicion = index)
                }
            }
        }
    }
}