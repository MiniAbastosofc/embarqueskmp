@file:OptIn(ExperimentalPermissionsApi::class)

package org.example.project

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File


@Composable
actual fun CameraView(onImageCaptured: (String) -> Unit) { // Agrega este parámetro
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    // 2. Comprobamos el estado del permiso
    when (cameraPermissionState.status) {
        is PermissionStatus.Granted -> {
            // El permiso está concedido. Mostramos la vista previa de la cámara.
            CameraPreviewActual(context, onImageCaptured, imageCapture) { newImageCapture ->
                imageCapture = newImageCapture
            }
        }

        is PermissionStatus.Denied -> {
            // El permiso está denegado. Mostramos un UI para pedir el permiso o explicar por qué se necesita.
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Lanzamos la solicitud de permiso automáticamente la primera vez que se entra aquí
                LaunchedEffect(Unit) {
                    cameraPermissionState.launchPermissionRequest()
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Se necesita permiso de cámara", modifier = Modifier.padding(16.dp))
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text("Permitir acceso a la cámara")
                    }
                }
            }
        }
    }
}

// Esta es una función separada que solo se llama si tenemos permisos
@Composable
fun CameraPreviewActual(
    context: Context,
    onImageCaptured: (String) -> Unit,
    imageCapture: ImageCapture?,
    onImageCaptureUpdate: (ImageCapture?) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    var showPreview by remember { mutableStateOf(false) }
    var capturedImagePath by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Vista de la cámara
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            update = { view ->
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    // Usamos una función de enlace segura
                    val newImageCapture = bindCameraUseCases(cameraProvider, view, lifecycleOwner)
                    onImageCaptureUpdate(newImageCapture)
                }, ContextCompat.getMainExecutor(context))
            }
        )

        // Botón para capturar foto
        Button(
            onClick = {
                imageCapture?.let { capture ->
                    takePicture(context, capture, onImageCaptured)
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .size(70.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Icon(
                Icons.Default.Camera,
                contentDescription = "Tomar foto",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }

        // Mostrar preview de la imagen capturada
        capturedImagePath?.let { path ->
            LaunchedEffect(path) {
                onImageCaptured(path)
            }
        }
    }
}

// Función para capturar foto
private fun takePicture(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (String) -> Unit
) {
    // Crear archivo para guardar la foto
    val photoFile = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "photo_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                onImageCaptured(savedUri.toString())
                // También puedes usar photoFile.absolutePath si prefieres la ruta del archivo
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", "Error al capturar foto: ${exception.message}", exception)
            }
        }
    )
}

// Función auxiliar para manejar el binding (enlace) de CameraX de forma limpia
fun bindCameraUseCases(
    cameraProvider: ProcessCameraProvider,
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner
): ImageCapture? {
    // Desvincula cualquier caso de uso anterior para evitar conflictos
    cameraProvider.unbindAll()

    val preview = Preview.Builder()
        .build()
        .also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

    val imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build()

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    return try {
        // Vincula el selector de cámara y la vista previa al ciclo de vida
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        imageCapture
    } catch (exc: Exception) {
        // Maneja errores si el binding falla (ej. si la cámara ya está en uso por otra app)
        Log.e("CameraView", "Binding failed", exc)
        null
    }
}