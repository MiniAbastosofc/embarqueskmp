package org.example.project.ui.home.tabs.sucursales

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import org.example.project.ui.home.tabs.rutas.components.RutasCalendar
import org.example.project.ui.home.tabs.rutas.components.RutasCard
import org.example.project.ui.home.tabs.sucursales.components.SucursalesCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SucursalesScreen(navController: NavHostController) {
    val sucursalesViewModel = koinViewModel<SucursalesViewModel>()
    val state by sucursalesViewModel.state.collectAsState()

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
                "Gestión de Sucursales",
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
                    "Buscar rutas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Calendario
                RutasCalendar(
                    fechaSeleccionada = state.fechaSeleccionada,
                    onDateSelected = { fecha ->
                        sucursalesViewModel.onDateSelected(fecha)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Búsqueda
                TextField(
                    value = state.searchText,
                    onValueChange = { sucursalesViewModel.onSearchTextChanged(it) },
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
                if (state.fechaSeleccionada != null || state.searchText.isNotEmpty()) {
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
                                sucursalesViewModel.onDateSelected(null)
                                sucursalesViewModel.onSearchTextChanged("")
                            }
                        ) {
                            Text("Limpiar", color = Color(0xFFF44336))
                        }
                    }
                }
            }
        }
        // --- Lista de Rutas ---
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
                    "Rutas Disponibles",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF37474F)
                )

                // Contador de rutas (puedes hacerlo dinámico)
                Text(
//                        "${state.embarques.size} rutas",
                    text = "prueba",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Lista de tarjetas de rutas
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                    items(items = state.embarques) { rutaModel ->
//                        RutasCard(
//                            navController = navController,
//                            ruta = rutaModel
//                        )
                item {
                    SucursalesCard()
                }
//                    }
            }
        }
    }
}