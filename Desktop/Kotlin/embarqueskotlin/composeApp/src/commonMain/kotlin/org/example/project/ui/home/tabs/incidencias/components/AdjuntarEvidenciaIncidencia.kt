package org.example.project.ui.home.tabs.incidencias.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.ui.home.tabs.incidencias.IncidenciasViewModel
import org.example.project.utils.LoadImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdjuntarEvidenciaIncidencia(imagenUri: String? = null) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
    {
        Text("Evidencia")
        Spacer(modifier = Modifier.height(10.dp))

        val hasImage = !imagenUri.isNullOrEmpty()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                // Cambia el comportamiento del clic si ya hay imagen, o deja el actual si no la hay
                .clickable(onClick = {
                    if (hasImage) {
                        println("Imagen clickeada: $imagenUri")
                        // Aquí podrías abrir un diálogo de imagen completa si lo deseas
                    } else {
                        println("Funciono, abrir cámara/galería")
                        // Lógica para abrir la cámara si aún no hay imagen
                    }
                })
                .height(160.dp)
                .padding(2.dp)
                // Aplica el borde punteado solo si no hay imagen (o si quieres que siempre esté)
                .dashedBorder(
                    color = if (hasImage) Color.Transparent else Color(0xFFD7DFE8),
                    strokeWidth = 2.dp,
                    cornerRadius = 12.dp,
                    dashLength = 10.dp,
                    gapLength = 6.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            if (hasImage) {
                // *** MUESTRA LA IMAGEN CAPTURADA AQUÍ ***
                LoadImage(
                    uri = imagenUri!!,
                    modifier = Modifier
                        .fillMaxSize()
                        // Aplica esquinas redondeadas al contenedor de la imagen
                        .graphicsLayer {
                            clip = true
                            shape = RoundedCornerShape(12.dp)
                        },
                    contentScale = ContentScale.Crop
                )
            } else {
                // Muestra el placeholder para adjuntar foto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.AddAPhoto,
                        contentDescription = "",
                        tint = Color(0xFF1173D4),
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Adjuntar Foto", color = Color(0xFF454D5D))
                }
            }
        }
    }
}

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp,
    cornerRadius: Dp = 0.dp,
    dashLength: Dp = 10.dp,
    gapLength: Dp = 6.dp
) = this.then(
    Modifier.drawBehind {
        val stroke = strokeWidth.toPx()
        val dash = dashLength.toPx()
        val gap = gapLength.toPx()
        val radius = cornerRadius.toPx()
        drawRoundRect(
            color = color,
            size = size,
            cornerRadius = CornerRadius(radius, radius),
            style = Stroke(
                width = stroke,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash, gap), 0f)
            )
        )
    }
)