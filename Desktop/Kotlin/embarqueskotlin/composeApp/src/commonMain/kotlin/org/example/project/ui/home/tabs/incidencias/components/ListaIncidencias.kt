package org.example.project.ui.home.tabs.incidencias.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.navigation.NavHostController
import org.example.project.data.remote.response.IncidenciaPorFechaResponse
import org.example.project.ui.home.tabs.incidencias.IncidenciasViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaIncidencias(navController: NavHostController) {
    val incidenciasViewModel = koinViewModel<IncidenciasViewModel>()
    val state by incidenciasViewModel.state.collectAsState()
    var fechaInput by remember { mutableStateOf("2026-02-02") }

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedIncidencia by remember { mutableStateOf<IncidenciaPorFechaResponse?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Buscador de Fecha
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = fechaInput,
                onValueChange = { fechaInput = it },
                label = { Text("Fecha (YYYY-MM-DD)") },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { incidenciasViewModel.cargarIncidenciasPorFecha(fechaInput) }) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Estados de la UI
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            state.error != null -> {
                Text("Error: ${state.error}", color = Color.Red)
            }

            state.listaIncidencias.isEmpty() -> {
                Text("No hay incidencias para esta fecha.")
            }

            else -> {
                LazyColumn {
                    items(state.listaIncidencias) { incidencia ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedIncidencia = incidencia
                                    showSheet = true
                                }
                        ) {
                            IncidenciaItem(incidencia)
                        }
                    }
                }
            }
        } // <--- Aquí termina el when
    } // <--- Aquí termina la Column
    // --- El Modal debe estar afuera para superponerse a la pantalla ---
    if (showSheet && selectedIncidencia != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            DetalleIncidenciaSheet(selectedIncidencia!!)
        }
    }
}
