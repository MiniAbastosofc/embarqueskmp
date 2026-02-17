package org.example.project.ui.home.tabs.productividad.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import org.example.project.domain.model.CajasProductividadModel
import org.example.project.ui.home.tabs.productividad.PlatformDatePicker
import org.example.project.ui.home.tabs.productividad.ProductividadViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CajasProductividadSemanalScreen() {
    val viewModel = koinViewModel<ProductividadViewModel>()
    val state by viewModel.state.collectAsState()

    // 0: Estibadores Cajas, 1: Checadores Cajas
    var tabSeleccionada by remember { mutableStateOf(0) }
    var showInicioPicker by remember { mutableStateOf(false) }
    var showFinPicker by remember { mutableStateOf(false) }

    // Solo cargamos el reporte de cajas
    LaunchedEffect(state.fechaInicio, state.fechaFin) {
        if (state.fechaInicio.isNotEmpty() && state.fechaFin.isNotEmpty()) {
            viewModel.cargarReporteCajas(state.fechaInicio, state.fechaFin)
        }
    }

    // DatePickers (Se mantienen igual)
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
            "Ranking de Cajas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        // Selectores de fecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { showInicioPicker = true }, modifier = Modifier.weight(1f)) {
                Text(if (state.fechaInicio.isEmpty()) "Inicio" else state.fechaInicio)
            }
            Button(onClick = { showFinPicker = true }, modifier = Modifier.weight(1f)) {
                Text(if (state.fechaFin.isEmpty()) "Fin" else state.fechaFin)
            }
        }

        Spacer(Modifier.height(16.dp))

        // Tabs solo para Cajas
        TabRow(selectedTabIndex = tabSeleccionada) {
            Tab(
                selected = tabSeleccionada == 0, onClick = { tabSeleccionada = 0 },
                text = { Text("Estibadores (${state.estibadoresCajas.size})") })
            Tab(
                selected = tabSeleccionada == 1, onClick = { tabSeleccionada = 1 },
                text = { Text("Checadores (${state.checadoresCajas.size})") })
        }

        if (state.isLoading) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        } else if (state.fechaInicio.isEmpty() || state.fechaFin.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Seleccione un rango de fechas", color = Color.Gray)
            }
        } else {
            val lista = if (tabSeleccionada == 0) state.estibadoresCajas else state.checadoresCajas
            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
                items(lista) { persona ->
                    CajasProductividadItem(item = persona)
                }
            }
        }
    }
}

@Composable
fun CajasProductividadItem(item: CajasProductividadModel) {
    // Definir color según la posición del ranking
    val badgeColor = when (item.posicion) {
        "1" -> Color(0xFFFFD700) // Oro
        "2" -> Color(0xFFC0C0C0) // Plata
        "3" -> Color(0xFFCD7F32) // Bronce
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con la posición
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = badgeColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = item.posicion,
                        fontWeight = FontWeight.Bold,
                        color = if (item.posicion.toIntOrNull() ?: 4 <= 3) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${item.cajasTotales} cajas en ${item.horasTrabajadas} hrs",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.cajasPorHora}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
                Text("Cajas/Hr", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}