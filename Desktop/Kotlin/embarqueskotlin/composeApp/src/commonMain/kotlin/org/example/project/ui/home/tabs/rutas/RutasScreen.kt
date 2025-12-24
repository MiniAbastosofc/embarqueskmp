@file:OptIn(ExperimentalTime::class)

package org.example.project.ui.home.tabs.rutas

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import org.example.project.ui.home.tabs.rutas.components.RutasCard
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime
import org.example.project.ui.home.tabs.rutas.components.RutasCalendar


@Composable
fun RutasScreen(navController: NavHostController) {
    val rutasViewModel = koinViewModel<RutasViewModel>()
    val state by rutasViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        rutasViewModel.loadEmbarques()
    }

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
                "Gestión de Rutas",
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
                        rutasViewModel.onDateSelected(fecha)
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Búsqueda
                TextField(
                    value = state.searchText,
                    onValueChange = { rutasViewModel.onSearchTextChanged(it) },
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
                    Row() {
                        TextButton(
                            onClick = { rutasViewModel.onEstadoSelected(EstadoRuta.PENDIENTE) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (state.filtroEstado == EstadoRuta.PENDIENTE)
                                    Color(0xFFFFFDE7) else Color.Transparent
                            )
                        ) {
                            Text(
                                "Sin Iniciar",
                                color = if (state.filtroEstado == EstadoRuta.PENDIENTE)
                                    Color(0xFFFFA000) else Color.Gray
                            )
                        }
                        TextButton(
                            onClick = { rutasViewModel.onEstadoSelected(EstadoRuta.EN_PROCESO) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (state.filtroEstado == EstadoRuta.EN_PROCESO)
                                    Color(0xFFFEE9D7) else Color.Transparent
                            )
                        ) {
                            Text(
                                "En Proceso",
                                color = if (state.filtroEstado == EstadoRuta.EN_PROCESO)
                                    Color(0xFFFA944D) else Color.Gray
                            )
                        }
                        TextButton(
                            onClick = { rutasViewModel.onEstadoSelected(EstadoRuta.COMPLETADO) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (state.filtroEstado == EstadoRuta.COMPLETADO)
                                    Color(0xFFE8F5E8) else Color.Transparent
                            )
                        ) {
                            Text(
                                "Completado",
                                color = if (state.filtroEstado == EstadoRuta.COMPLETADO)
                                    Color(0xFF26945A) else Color.Gray
                            )
                        }
                    }

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
                                rutasViewModel.onDateSelected(null)
                                rutasViewModel.onSearchTextChanged("")
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
                    "${state.embarques.size} rutas",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Lista de tarjetas de rutas
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = state.embarquesFiltrados) { rutaModel ->  // Ejemplo con 5 items
                    RutasCard(
                        navController = navController,
                        ruta = rutaModel
                    )
                }
            }
        }

        // --- Espacio inferior ---
        Spacer(modifier = Modifier.height(16.dp))
    }
}
