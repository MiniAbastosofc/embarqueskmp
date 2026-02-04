package org.example.project.ui.home.tabs.rutas.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.example.project.CameraView
import org.example.project.data.UserDataManager
import org.example.project.domain.model.PersonalBodegaModel
import org.example.project.ui.core.navigation.Routes
import org.example.project.ui.home.tabs.rutas.RutaUiState
import org.example.project.ui.home.tabs.rutas.RutasViewModel
import org.example.project.ui.home.tabs.rutas.UploadFotoState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapturaRutas(
    navController: NavHostController,
    rutasViewModel: RutasViewModel = koinViewModel(),
    rutaId: String?,
    statusId: Int?
) {
//    var text by remember { mutableStateOf("") }
    var showConfirmDialog by remember { mutableStateOf(false) }
    val state by rutasViewModel.state.collectAsState()
//    var fotoPath by remember { mutableStateOf<String?>(null) }
    var showPreview by remember { mutableStateOf(false) }
    val estadoActual = state.currentRutaStatus ?: statusId ?: 1
    println("${rutaId} - ${statusId} Aqui vemos que llega")
    val iniciarRutaState by rutasViewModel.iniciarRutaState.collectAsState()
    val user = UserDataManager.getCurrentUser()
    val usuarioId = user?.UsuarioId

    LaunchedEffect(Unit) {
        if (statusId != null && rutasViewModel.state.value.currentRutaStatus == null) {
            rutasViewModel.setCurrentRutaStatus(statusId)
        }
        println("Aqui vemos la ruta - ${rutaId}")
    }

    LaunchedEffect(state.uploadFotoState) {
        when (val uploadState = state.uploadFotoState) {
            is UploadFotoState.Success -> {
                println("✅ ${uploadState.message}")
                // El comentario e imagen ya se reiniciaron automáticamente en el ViewModel
                // Mostrar mensaje de éxito si quieres

                // Opcional: resetear el estado después de un tiempo
                delay(2000) // 2 segundos
                rutasViewModel.resetUploadFotoState()
            }

            is UploadFotoState.Error -> {
                println("❌ ${uploadState.error}")
                // En error NO se reinician los campos, el usuario puede intentar de nuevo
                delay(2000)
                rutasViewModel.resetUploadFotoState()
            }

            else -> {}
        }
    }


    LaunchedEffect(iniciarRutaState) {
        if (iniciarRutaState is RutaUiState.Success) {
            // Muestra un Toast o Snackbar con el mensaje (depende de tu implementación)
            println("TOAST: ${(iniciarRutaState as RutaUiState.Success).message}")
            rutasViewModel.resetIniciarRutaState()
            // Como ya actualizamos el 'currentRutaStatus' en el VM, el `when` cambiará automáticamente a 2.
        }
        if (iniciarRutaState is RutaUiState.Error) {
            // Muestra un AlertDialog con el error
            println("ERROR DIALOG: ${(iniciarRutaState as RutaUiState.Error).errorMessage}")
            rutasViewModel.resetIniciarRutaState()
        }
    }

    LaunchedEffect(state.embarqueFinalizado, state.finalizarEmbarqueError) {
        if (state.embarqueFinalizado) {
            println("=== DATOS DEL EMBARQUE FINALIZADO ===")
            println("📸 Imagen: ${state.imagenUri}")
            println("💬 Comentario: ${state.comentario}")
            println("📅 Fecha: ${state.fechaSeleccionada}")
            println("✅ Estado: ${state.embarqueFinalizado}")

            // Navegar después de completar
            navController.navigate(Routes.Rutas.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
        // Mostrar error si existe
        state.finalizarEmbarqueError?.let { error ->
            println("❌ Error al finalizar: $error")
            // Aquí puedes mostrar un snackbar con el error
            // Y limpiar el error después de mostrarlo
            rutasViewModel.clearFinalizarError()
        }
    }

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
            // Botón de regresar alineado a la izquierda
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
            // Título centrado
            Text(
                text = "Asignaciones",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        when (estadoActual) {
            1 -> {   // SIN INICIAR
                // Campos de estado
                var tonelaje by remember { mutableStateOf("") }
                var checadorSeleccionado by remember { mutableStateOf<PersonalBodegaModel?>(null) }
                var estibadorSeleccionado by remember { mutableStateOf<PersonalBodegaModel?>(null) }
                var expandedChecador by remember { mutableStateOf(false) }
                var expandedEstibador by remember { mutableStateOf(false) }
                val checadores = state.checadores
                val estibadores = state.estibadores

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Texto informativo
                    Text(
                        text = "Complete los datos para iniciar el embarque",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    // Selector de Checador
                    ExposedDropdownMenuBox(
                        expanded = expandedChecador,
                        onExpandedChange = { expandedChecador = !expandedChecador }
                    ) {
                        OutlinedTextField(
                            value = if (checadorSeleccionado != null)
                                "${checadorSeleccionado?.nombre} ${checadorSeleccionado?.apellido}"
                            else "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .padding(bottom = 12.dp),
                            readOnly = true,
                            label = { Text("Checador *") },
                            placeholder = { Text("Seleccione un checador") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedChecador)
                            },
                            shape = RoundedCornerShape(12.dp),
                            //colors = TextFieldDefaults.outlinedTextFieldColors(
                            //    focusedBorderColor = Color(0xFF64B5F6),
                            //    unfocusedBorderColor = Color.Gray
                            //)
                        )

                        ExposedDropdownMenu(
                            expanded = expandedChecador,
                            onDismissRequest = { expandedChecador = false }
                        ) {
                            checadores.forEach { checador ->
                                DropdownMenuItem(
                                    text = { Text("${checador.nombre} ${checador.apellido}") },
                                    onClick = {
                                        checadorSeleccionado = checador
                                        expandedChecador = false

                                        val idParaEnviar = checador.id
                                        println("ID seleccionado: $idParaEnviar")
                                    }
                                )
                            }
                        }
                    }

                    // Selector de Estibador
                    ExposedDropdownMenuBox(
                        expanded = expandedEstibador,
                        onExpandedChange = { expandedEstibador = !expandedEstibador }
                    ) {
                        OutlinedTextField(
                            value = if (estibadorSeleccionado != null)
                                "${estibadorSeleccionado?.nombre} ${estibadorSeleccionado?.apellido}"
                            else "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .padding(bottom = 12.dp),
                            readOnly = true,
                            label = { Text("Estibador *") },
                            placeholder = { Text("Seleccione un estibador") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstibador)
                            },
                            shape = RoundedCornerShape(12.dp),
                            //colors = TextFieldDefaults.outlinedTextFieldColors(
                            //    focusedBorderColor = Color(0xFF64B5F6),
                            //    unfocusedBorderColor = Color.Gray
                            //)
                        )

                        ExposedDropdownMenu(
                            expanded = expandedEstibador,
                            onDismissRequest = { expandedEstibador = false }
                        ) {
                            estibadores.forEach { estibador ->
                                DropdownMenuItem(
                                    text = { Text("${estibador.nombre} ${estibador.apellido}") },
                                    onClick = {
                                        estibadorSeleccionado = estibador
                                        expandedEstibador = false
                                        val idEstibador = estibador.id
                                        println("ID seleccionado: $idEstibador")
                                    }
                                )
                            }
                        }
                    }
                    // Campo de texto para tonelaje
                    OutlinedTextField(
                        value = tonelaje,
                        onValueChange = { nuevoValor ->
                            // Filtrar solo números
                            if (nuevoValor.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
                                tonelaje = nuevoValor
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        label = { Text("Tonelaje *") },
                        placeholder = { Text("Ejemplo: 12.5 o 0.05") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        //colors = TextFieldDefaults.outlinedTextFieldColors(
                        //    focusedBorderColor = Color(0xFF64B5F6),
                        //    unfocusedBorderColor = Color.Gray
                        //)
                    )

                    Button(
                        onClick = {
                            val embarqueID = rutaId?.toIntOrNull() ?: 0
                            val usuarioID = usuarioId
                            val tonelajeValor = tonelaje.toDoubleOrNull() ?: 0.0
                            val checadorID = checadorSeleccionado?.id ?: 0
                            val estibadorID = estibadorSeleccionado?.id ?: 0

                            if (embarqueID > 0) {
                                // Aquí pasarías también los IDs del checador y estibador
                                rutasViewModel.iniciarRuta(
                                    embarqueID, usuarioID, tonelajeValor, checadorId = checadorID,
                                    estibadorId = estibadorID
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF64B5F6),
                            disabledContainerColor = Color(0xFFBDBDBD)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = tonelaje.isNotEmpty() &&
                                checadorSeleccionado != null &&
                                estibadorSeleccionado != null
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Iniciar",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Iniciar Embarque",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    // Mensaje de validación
                    if (tonelaje.isEmpty() || checadorSeleccionado == null || estibadorSeleccionado == null) {
                        val mensajes = mutableListOf<String>()
                        if (tonelaje.isEmpty()) mensajes.add("tonelaje")
                        if (checadorSeleccionado == null) mensajes.add("checador")
                        if (estibadorSeleccionado == null) mensajes.add("estibador")

                        Text(
                            text = "Debe completar: ${mensajes.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFE53935),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }

                return
            }

            2 -> {
                // --- Sección de cámara/imagen ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (showPreview && state.imagenUri != null) {
                            // Mostrar preview de la imagen capturada
                            state.imagenUri?.let { uri ->
                                CapturedImagePreview(uri) {
                                    showPreview = false
                                    // Si quieres que al cerrar el preview vuelva a mostrar la cámara
                                    // rutasViewModel.onImagenCapturada(null)
                                }
                            }
                        } else {
                            // Mostrar la cámara
                            CameraView { path ->
                                rutasViewModel.onImagenCapturada(path)
                                showPreview = true
                            }
                        }
                    }
                }
                // --- Estado de la foto ---
                state.imagenUri?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E8)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF26945A),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "Foto capturada exitosamente",
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }
                }
                // --- BARRA DE PROGRESO AGREGADA AQUÍ ---
                Column(
                    modifier = Modifier
                        .weight(1f) // ← Esto es importante para que ocupe el espacio restante
                        .verticalScroll(rememberScrollState())
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            // Texto del porcentaje centrado y más discreto
                            Text(
                                text = "${state.porcentajeAvance}% completado",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF666666),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Slider minimalista
                            Slider(
                                value = state.porcentajeAvance.toFloat(),
                                onValueChange = { newValue ->
                                    rutasViewModel.setProgress(newValue.toInt())
                                },
                                valueRange = 0f..100f,
                                steps = 99,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(20.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFF42A5F5), // Azul un poco más vibrante
                                    activeTrackColor = Color(0xFF42A5F5),
                                    inactiveTrackColor = Color(0xFFBBDEFB), // Azul muy claro para el track inactivo
                                )
                            )
                        }
                    }
                    // --- Campo de comentario ---
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Comentarios",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            TextField(
                                value = state.comentario,
                                onValueChange = { rutasViewModel.onComentarioChange(it) },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Agregar comentario opcional...") },
                                shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color(0xFFF5F9FF),
                                    unfocusedContainerColor = Color(0xFFF5F9FF),
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    cursorColor = Color(0xFF64B5F6),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                    // --- Botones de acción ---
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Botón Enviar
                        Button(
                            onClick = {
                                // Acción de enviar
                                val embarqueID = rutaId?.toIntOrNull() ?: 0
                                val usuarioID = usuarioId // Tu usuario ID real
                                val filePath = state.imagenUri ?: ""

                                if (filePath.isNotEmpty() && embarqueID > 0) {
                                    // Llamar a la función del ViewModel para enviar la foto
                                    rutasViewModel.agregarFoto(
                                        embarqueID = embarqueID,
                                        comentario = state.comentario,
                                        porcentajeAvance = state.porcentajeAvance,
                                        usuarioID = usuarioID,
                                        filePath = filePath
                                    )
                                    println("Comentario a enviar: ${state.comentario}")
                                    println("Imagen a enviar: $filePath")
                                    println("Porcentaje a enviar: ${state.porcentajeAvance}%")
                                } else {
                                    println("❌ Faltan datos requeridos: imagen o embarqueID")
                                    if (filePath.isEmpty()) println("❌ No hay imagen capturada")
                                    if (embarqueID <= 0) println("❌ ID de embarque inválido: $rutaId")
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF64B5F6)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            ),
                            enabled = state.uploadFotoState !is UploadFotoState.Loading && state.imagenUri != null
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Enviar",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Enviar", fontWeight = FontWeight.Medium)
                        }

                        // Botón Finalizar
                        Button(
                            onClick = {
                                showConfirmDialog = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF81C784)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            ),
                            enabled = !state.finalizandoEmbarque
                        ) {
                            if (state.finalizandoEmbarque) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    Icons.Default.DoneAll,
                                    contentDescription = "Finalizar",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                            Text(
                                if (state.finalizandoEmbarque) "Finalizando..." else "Finalizar",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextButton(onClick = {
                            val currentUri = state.imagenUri
                            // 2. Construye la ruta con la URI
                            val destinationRoute = Routes.Incidencias.withParams(
                                embarqueId = rutaId ?: "",
                                imagenUri = currentUri
                            )
                            // 3. Navega a la nueva ruta
                            navController.navigate(destinationRoute)
                        }) {
                            Text("Agregar Incidencia")
                        }
                        TextButton(onClick = {
                            println("Funcion boton pausar embarque - ${rutaId}")
                            if (rutaId != null && usuarioId != null) {
                                val route = Routes.Pausas.createRoute(
                                    rutaId = rutaId,
                                    usuarioId = usuarioId,
                                )
                                navController.navigate(route)
                            } else {
                                println("Error: rutaId o usuarioId nulos")
                            }
                        }) {
                            Text("Pausar Embarque")
                        }
                    }
                }
                // --- Diálogo de confirmación ---
                if (showConfirmDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            if (!state.finalizandoEmbarque) {
                                showConfirmDialog = false
                            }
                        },
                        title = {
                            Text(
                                "Confirmar finalización",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        text = {
                            Text("¿Estás seguro de que deseas finalizar el embarque?")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    rutasViewModel.finalizarEmbarque(rutaId, usuarioId)
                                    navController.navigate(Routes.Rutas.route) {
                                        popUpTo(0)
                                        launchSingleTop = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF81C784)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Sí, finalizar", fontWeight = FontWeight.Medium)
                            }
                        },
                        dismissButton = {
                            OutlinedButton(
                                onClick = { showConfirmDialog = false },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Color.Gray)
                            ) {
                                Text("Cancelar", color = Color.Gray)
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        containerColor = Color.White
                    )
                }
            }

            3 -> {   // COMPLETADO
                Text(
                    "Este embarque ya fue completado.",
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.SemiBold
                )
                return
            }
        }
    }
}

@Composable
fun CapturedImagePreview(imagePath: String, onRetake: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Preview de la imagen con Coil
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            AsyncImage(
                model = imagePath,
                contentDescription = "Foto capturada",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Botón para tomar otra foto
        Button(
            onClick = onRetake,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6)),
            shape = RoundedCornerShape(32.dp)
        ) {
            Icon(Icons.Default.Camera, contentDescription = "Tomar otra foto")
            Spacer(Modifier.width(8.dp))
            Text("Tomar Otra Foto")
        }
    }
}

