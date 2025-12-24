package org.example.project.ui.home.tabs.historial.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.utils.toFormattedDate

@Composable
fun HistorialDetalleCard(details: DetalleEmbarqueCompleto) {
    // Determinar qué texto mostrar para el título
    val titulo = if (details.info.tipoEmbarque == "SUCURSAL") {
        "${details.info.almOrigen} - ${details.info.desAlmOrigen}"
    } else {
        "${details.info.camionID} - ${details.info.nombreCamion}"
    }

    // Determinar qué texto mostrar para la ruta/destino
    val subtitulo = if (details.info.tipoEmbarque == "SUCURSAL") {
        "Destino: ${details.info.almDestino} - ${details.info.desAlmDestino}"
    } else {
        "Ruta: ${details.info.rutaID} - ${details.info.nombreRuta}"
    }

    val (texto, colorFondo, colorTexto) = when (details.info.estadoID) {
        2 -> Triple("En Proceso", Color(0xFFFEE9D7), Color(0xFFFA944D))
        3 -> Triple("Completado", Color(0xFFE8F5E8), Color(0xFF26945A))
        else -> Triple("Estado ${details.info.estadoID}", Color(0xFFF5F5F5), Color(0xFF757575))
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
            modifier = Modifier.padding(20.dp)
        ) {
            // --- Header con información del autobús y estado ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        titulo,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A237E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        subtitulo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF546E7A)
                    )
                }

                // Badge de estado
                Box(
                    modifier = Modifier
                        .background(color = colorFondo, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = texto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorTexto
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Información de tiempo ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Fila de inicio
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Inicio",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Iniciado",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF546E7A),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        details.info.fechaInicio?.toFormattedDate() ?: "Fecha no disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF263238),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Fila de fin
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (details.info.fechaCompletado != null) Icons.Default.Check else Icons.Default.PlayArrow,
                            contentDescription = "Estado",
                            tint = if (details.info.fechaCompletado != null) Color(0xFF4CAF50) else Color(
                                0xFFFF9800
                            ),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (details.info.fechaCompletado != null) "Completado" else "En Proceso",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (details.info.fechaCompletado != null) Color(0xFF4CAF50) else Color(
                                0xFFFF9800
                            ),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        details.info.fechaCompletado?.toFormattedDate() ?: "En curso",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (details.info.fechaCompletado != null) Color(0xFF263238) else Color(
                            0xFF757575
                        ),
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = if (details.info.fechaCompletado == null) FontStyle.Italic else FontStyle.Normal
                    )
                }

                // Fila de duración
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Duración",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Duración",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF546E7A),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        "${details.info.duracionFormateada}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Contenido de la IZQUIERDA (icono + "Usuario:")
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Usuario",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Usuario:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF546E7A),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    // Contenido de la DERECHA (nombre del usuario)
                    Text(
                        text = details.info.nombreUsuarioCompletado ?: details.info.usuarioCreacion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF546E7A),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Progreso del embarque
                Column {
                    Text(
                        "Progreso del embarque",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF757575)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { details.info.porcentajeAvance / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = Color(0xFF4CAF50),
                        trackColor = Color(0xFFE0E0E0),
                        strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                    )
                }

//                    Spacer(modifier = Modifier.width(12.dp))

                // Porcentaje
                Text(
                    text = "${details.info.porcentajeAvance}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}
