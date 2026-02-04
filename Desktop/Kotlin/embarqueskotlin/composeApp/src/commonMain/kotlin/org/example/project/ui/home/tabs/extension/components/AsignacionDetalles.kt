package org.example.project.ui.home.tabs.extension.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.home.tabs.extension.ExtensionViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AsignacionDetalle() {
    val extensionViewModel = koinViewModel<ExtensionViewModel>()
    val state by extensionViewModel.state.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
//                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Asignación de Detalle",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Campos del formulario con mejor espaciado
            OutlinedTextField(
                value = state.pruebaState,
                onValueChange = { extensionViewModel.onChecadorChange(it) },
                label = { Text("Checador") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Checador"
                    )
                }
            )

            OutlinedTextField(
                value = state.pruebaState,
                onValueChange = { /* Agregar función para Estibador */ },
                label = { Text("Estibador") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Groups,
                        contentDescription = "Estibador"
                    )
                }
            )

            OutlinedTextField(
                value = state.motivoExtensionState,
                onValueChange = { /* Agregar función para Motivo */ },
                label = { Text("Motivo de la extensión") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = MaterialTheme.shapes.medium,
                maxLines = 3,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Description,
                        contentDescription = "Motivo"
                    )
                }
            )

            // Sección para rutas con separador visual
            Text(
                text = "Información de Rutas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp)
            )

            OutlinedTextField(
                value = state.rutaOrigenState,
                onValueChange = { /* Agregar función para Origen */ },
                label = { Text("Ruta/Sucursal Origen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = "Origen",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            // Icono de flecha entre los campos de ruta
            Icon(
                Icons.Outlined.ArrowDownward,
                contentDescription = "Destino",
                modifier = Modifier
                    .size(24.dp)
                    .padding(vertical = 4.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = state.rutaDestinoState,
                onValueChange = { /* Agregar función para Destino */ },
                label = { Text("Ruta/Sucursal Destino") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Place,
                        contentDescription = "Destino",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            // Botón con mejor diseño
            Button(
                onClick = { /* Acción para iniciar embarque */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Icon(
                    Icons.Outlined.PlayArrow,
                    contentDescription = "Iniciar",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    "Iniciar Embarque",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}