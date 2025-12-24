@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.project.ui.home.tabs.historial.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.example.project.domain.model.DetalleEmbarqueFoto
import org.example.project.utils.formatToTime

@Composable
fun HistorialFotosCard(foto: DetalleEmbarqueFoto) {
    var showImageFullScreen by remember { mutableStateOf(false) }
    val fullUrl = "http://172.16.4.187:3099/embarqueskotlin${foto.urlFoto}"
    // BottomSheet para mostrar imagen completa
    if (showImageFullScreen) {
        ModalBottomSheet(
            onDismissRequest = { showImageFullScreen = false },
            sheetState = rememberModalBottomSheetState(),
            containerColor = Color.Black,
            scrimColor = Color.Black.copy(alpha = 0.8f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                // Imagen en tamaño completo
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = fullUrl,
                        contentDescription = "Imagen capturada",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Vista completa de la imagen",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Aquí se mostraría la imagen real capturada",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // --- Header con timestamp ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Información de tiempo
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Icon(
//                        imageVector = Icons.Default.Schedule,
//                        contentDescription = "Tiempo",
//                        tint = Color(0xFF757575),
//                        modifier = Modifier.size(16.dp)
//                    )
                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        "Hace ${foto.fechaCreacion.formatToTime()}",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = Color(0xFF757575),
//                        fontWeight = FontWeight.Medium
//                    )
                }
                // Badge de estado
                if (foto.comentario != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE3F2FD),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "Con Comentario",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF1976D2),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // --- Preview de la imagen (CLICKABLE) ---
            val fullUrl = "http://172.16.4.187:3099/embarqueskotlin${foto.urlFoto}"
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showImageFullScreen = true
                    },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    // Aquí va tu imagen - por ahora un placeholder
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = fullUrl,
                            contentDescription = "Imagen capturada",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Toca para ver imagen completa",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF9E9E9E)
                        )
                    }

                    // Overlay de información en esquina
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.7f), CircleShape)
                                .padding(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Foto",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Indicador de que es clickeable
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    Color.Black.copy(alpha = 0.6f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "Ver imagen completa",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Comentario ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Comment,
                            contentDescription = "Comentario",
                            tint = Color(0xFF5C6BC0),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Comentario",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF37474F)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    if (!foto.comentario.isNullOrEmpty()) {
                        Text(
                            text = foto.comentario ?: "", // ← Esto asegura que nunca sea null
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF546E7A),
                            lineHeight = 20.sp
                        )
                    } else {
                        Text(
                            text = "Sin comentario",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFB0BEC5),
                            fontStyle = FontStyle.Italic,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}