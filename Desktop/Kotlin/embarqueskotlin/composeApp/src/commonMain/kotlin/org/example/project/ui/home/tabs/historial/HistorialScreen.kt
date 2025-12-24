package org.example.project.ui.home.tabs.historial

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.historial.components.HistorialCard
import org.example.project.ui.home.tabs.rutas.components.RutasCalendar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistorialScreen(navController: NavHostController) {
    val historialViewModel = koinViewModel<HistorialViewModel>()
    val state by historialViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // --- Header Mejorado ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Historial de Rutas",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        // --- Filtros en Tarjeta ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Título de la sección de filtros
                Text(
                    "Filtrar por",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                // Calendario
                RutasCalendar(
                    fechaSeleccionada = state.fechaSeleccionada,
                    onDateSelected = { fecha ->
                        historialViewModel.onDateSelected(fecha)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // Búsqueda
                TextField(
                    value = state.prueba,
                    onValueChange = { historialViewModel.onComentarioChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color(0xFF64B5F6)
                        )
                    },
                    placeholder = {
                        Text("Buscar por camión, ruta o conductor...")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F9FF),
                        unfocusedContainerColor = Color(0xFFF5F9FF),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(0xFF64B5F6),
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color(0xFF64B5F6),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                // Estado actual de los filtros
                if (state.fechaSeleccionada != null || state.prueba.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Filtros activos:",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )

                        TextButton(
                            onClick = {
                                // Opcional: agregar método para limpiar filtros
                                historialViewModel.onDateSelected(null)
                                historialViewModel.onComentarioChange("")
                            }
                        ) {
                            Text("Limpiar", color = Color(0xFFF44336))
                        }
                    }
                }
            }
        }
        // --- Resultados ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Header de resultados
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Resultados",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF37474F)
                )
                // Contador de resultados (puedes calcularlo según tus datos)
                Text(
                    "${state.shipments.size} rutas encontradas", // Esto sería dinámico
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            // Lista de tarjetas de historial
            when {
                state.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error: ${state.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                // Cuando tenemos datos, mostramos la lista
                // --- Espacio inferior ---
                //Spacer(modifier = Modifier.height(16.dp))
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Usamos state.shipments aquí
                        items(state.shipments, key = { it.id }) { shipment ->
                            HistorialCard(
                                navController = navController,
                                shipmentDetails = shipment // Pasamos los datos a la tarjeta
                            )
                        }
                    }
                }
            }
        }
    }
}