package org.example.project.ui.home.tabs.admin.AdminUsuarios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.admin.AdminEvent
import org.example.project.ui.home.tabs.admin.AdminState
import org.example.project.ui.home.tabs.admin.AdminViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminCrearUsuario(
    navController: NavHostController,
    viewModel: AdminViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Manejar errores con Snackbar
    LaunchedEffect(state.crearUsuarioError) {
        state.crearUsuarioError?.let { error ->
            if (error.isNotBlank()) {
                snackbarHostState.showSnackbar(
                    message = error,
                    duration = SnackbarDuration.Long
                )
                // Limpiar el error después de mostrarlo
                viewModel.onEvent(AdminEvent.CrearUsuarioErrorChanged(null))
            }
        }
    }

    // Manejar éxito
    LaunchedEffect(state.crearUsuarioId) {
        if (state.crearUsuarioId.isNotEmpty() && !state.crearUsuarioLoading) {
            snackbarHostState.showSnackbar(
                message = "✅ Usuario creado exitosamente",
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                }
                Text(
                    "Crear Usuario",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Formulario
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // ID Generado Temporal
                    if (state.crearUsuarioId.isNotEmpty() && state.crearUsuarioLoading) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color(0xFF1976D2)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("ID Temporal:", style = MaterialTheme.typography.bodySmall)
                                    Text(
                                        state.crearUsuarioId,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Mensaje de éxito después de crear
                    if (state.crearUsuarioId.isNotEmpty() && !state.crearUsuarioLoading) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF4CAF50)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        "Usuario Creado:",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF2E7D32)
                                    )
                                    Text(
                                        state.crearUsuarioId,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Campos del formulario con validación visual
                    OutlinedTextField(
                        value = state.crearUsuarioNombre,
                        onValueChange = { viewModel.onEvent(AdminEvent.CrearUsuarioNombreChanged(it)) },
                        label = { Text("Nombre *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        isError = state.crearUsuarioNombre.isBlank() && state.crearUsuarioError != null,
                        supportingText = {
                            if (state.crearUsuarioNombre.isBlank() && state.crearUsuarioError != null) {
                                Text("El nombre es requerido")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = state.crearUsuarioApellido,
                        onValueChange = {
                            viewModel.onEvent(
                                AdminEvent.CrearUsuarioApellidoChanged(
                                    it
                                )
                            )
                        },
                        label = { Text("Apellido *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        leadingIcon = { Icon(Icons.Default.PersonOutline, null) },
                        isError = state.crearUsuarioApellido.isBlank() && state.crearUsuarioError != null,
                        supportingText = {
                            if (state.crearUsuarioApellido.isBlank() && state.crearUsuarioError != null) {
                                Text("El apellido es requerido")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = state.crearUsuarioNumeroEmpleado,
                        onValueChange = {
                            viewModel.onEvent(
                                AdminEvent.CrearUsuarioNumeroEmpleadoChanged(it)
                            )
                        },
                        label = { Text("Número de Empleado *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        leadingIcon = { Icon(Icons.Default.Badge, null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = state.crearUsuarioNumeroEmpleado.isBlank() && state.crearUsuarioError != null,
                        supportingText = {
                            if (state.crearUsuarioNumeroEmpleado.isBlank() && state.crearUsuarioError != null) {
                                Text("El número de empleado es requerido")
                            }
                        }
                    )

                    // Selector de Rol
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = state.crearUsuarioRol,
                            onValueChange = {},
                            label = { Text("Rol *") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Work, null) },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    null,
                                    tint = if (state.crearUsuarioRol.isBlank() && state.crearUsuarioError != null) Color.Red else Color.Unspecified
                                )
                            },
                            readOnly = true,
                            enabled = false,
                            isError = state.crearUsuarioRol.isBlank() && state.crearUsuarioError != null,
                            supportingText = {
                                if (state.crearUsuarioRol.isBlank() && state.crearUsuarioError != null) {
                                    Text("Selecciona un rol")
                                }
                            }
                        )

                        // Surface clickable sobre todo el campo
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable { expanded = true },
                            color = Color.Transparent
                        ) {}

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf("Administrador", "Auditor").forEach { rol ->
                                DropdownMenuItem(
                                    text = { Text(rol) },
                                    onClick = {
                                        viewModel.onEvent(AdminEvent.CrearUsuarioRolChanged(rol))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Contraseña
                    OutlinedTextField(
                        value = state.crearUsuarioContrasena,
                        onValueChange = {
                            viewModel.onEvent(
                                AdminEvent.CrearUsuarioContrasenaChanged(
                                    it
                                )
                            )
                        },
                        label = { Text("Contraseña *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.onEvent(AdminEvent.CrearUsuarioMostrarContrasenaChanged(!state.crearUsuarioMostrarContrasena))
                            }) {
                                Icon(
                                    if (state.crearUsuarioMostrarContrasena) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    null
                                )
                            }
                        },
                        visualTransformation = if (state.crearUsuarioMostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = (state.crearUsuarioContrasena.isBlank() || state.crearUsuarioContrasena.length < 6) && state.crearUsuarioError != null,
                        supportingText = {
                            if (state.crearUsuarioContrasena.isBlank() && state.crearUsuarioError != null) {
                                Text("La contraseña es requerida")
                            } else if (state.crearUsuarioContrasena.length < 6 && state.crearUsuarioContrasena.isNotBlank()) {
                                Text("Mínimo 6 caracteres")
                            }
                        }
                    )

                    // Botón Crear Usuario
                    Button(
                        onClick = { viewModel.onEvent(AdminEvent.CrearUsuario) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = isFormularioValido(state) && !state.crearUsuarioLoading,
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = Color(0xFFCCCCCC),
                            disabledContentColor = Color(0xFF666666)
                        )
                    ) {
                        if (state.crearUsuarioLoading) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Creando Usuario...", fontWeight = FontWeight.Medium)
                            }
                        } else {
                            Text("Crear Usuario", fontWeight = FontWeight.Bold)
                        }
                    }

                    // Botón Limpiar (solo si hay datos)
                    if (state.crearUsuarioNombre.isNotBlank() || state.crearUsuarioApellido.isNotBlank() ||
                        state.crearUsuarioNumeroEmpleado.isNotBlank() || state.crearUsuarioRol.isNotBlank() ||
                        state.crearUsuarioContrasena.isNotBlank()
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { viewModel.onEvent(AdminEvent.LimpiarFormulario) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text("Limpiar Formulario")
                        }
                    }
                }
            }

            // Información de ayuda
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF1976D2)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "El nombre de usuario se generará automáticamente con el formato: nombre.apellido",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}

private fun isFormularioValido(state: AdminState): Boolean {
    return state.crearUsuarioNombre.isNotBlank() &&
            state.crearUsuarioApellido.isNotBlank() &&
            state.crearUsuarioNumeroEmpleado.isNotBlank() &&
            state.crearUsuarioRol.isNotBlank() &&
            state.crearUsuarioContrasena.isNotBlank() &&
            state.crearUsuarioContrasena.length >= 6
}