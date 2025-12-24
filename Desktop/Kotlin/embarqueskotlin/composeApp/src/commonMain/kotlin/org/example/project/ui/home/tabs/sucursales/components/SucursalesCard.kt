package org.example.project.ui.home.tabs.sucursales.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.core.navigation.Routes
import org.example.project.ui.home.tabs.rutas.components.EstadoEstilo

@Composable
fun SucursalesCard() {
    val isEnabled = true // Deshabilitada solo si es 3 (Completado)
    val cardAlpha = if (isEnabled) 1f else 0.5f
    val ruta = 1
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(enabled = isEnabled) {
//                navController.navigate(
//                    Routes.CapturaRutas.withId(
//                        ruta.id,
//                        ruta.statusId
//                    )
//                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = cardAlpha)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header con icono y información principal
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFFE3F2FD).copy(alpha = cardAlpha),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = "Camión",
                            tint = Color(0xFF1976D2).copy(alpha = cardAlpha),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
//                            text = "${ruta.camion} - ${ruta.camionDescripcion}",
                            text = "Suc Origen: 001 - Cedis Oaxaca",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333).copy(alpha = cardAlpha)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Suc Destino: 021 - ETLA",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF666666).copy(alpha = cardAlpha)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Línea separadora
            Divider(
                color = Color(0xFFF0F0F0),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val estilo = when (ruta) { // Corregí a statusId según tu error log
                1 -> EstadoEstilo(
                    "Sin Iniciar",
                    Color(0xFFFFA000),
                    Color(0xFFFFFDE7),
                    Icons.Default.Info
                )

                2 -> EstadoEstilo(
                    "En Proceso",
                    Color(0xFF1976D2),
                    Color(0xFFE3F2FD),
                    Icons.Default.HourglassBottom
                )

                3 -> EstadoEstilo(
                    "Completado",
                    Color(0xFF26945A),
                    Color(0xFFE8F5E8),
                    Icons.Default.CheckCircle
                )

                else -> EstadoEstilo("Desconocido", Color.Gray, Color.LightGray, Icons.Default.Info)
            }

            // Footer con estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge de estado
                Box(
                    modifier = Modifier
                        .background(
                            color = estilo.bgColor.copy(alpha = cardAlpha),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = estilo.texto,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = estilo.color.copy(alpha = cardAlpha)
                    )
                }

                // Indicador adicional (opcional)
                Icon(
                    imageVector = estilo.icono, // Usa la propiedad icono
                    contentDescription = estilo.texto,
                    tint = estilo.color.copy(alpha = cardAlpha),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}