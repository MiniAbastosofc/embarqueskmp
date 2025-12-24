package org.example.project.ui.home.tabs.historial.components

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
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.example.project.domain.model.RutaDetallesModel
import org.example.project.ui.core.navigation.Routes

@Composable
fun HistorialCard(navController: NavHostController, shipmentDetails: RutaDetallesModel) {
    Column() {
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable {
                    navController.navigate(
                        Routes.DetallesHistorial.withId(shipmentDetails.id)
                    )
                },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
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
                                    color = Color(0xFFE3F2FD),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = "Camión",
                                tint = Color(0xFF1976D2),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            // --- Línea principal (camión o sucursal) ---
                            val tituloPrincipal = when {
                                // Movimiento entre almacenes
                                shipmentDetails.almOrigen != null && shipmentDetails.almDestino != null ->
                                    "${shipmentDetails.desAlmOrigen} → ${shipmentDetails.desAlmDestino}"

                                // Ruta normal (camión)
                                else -> "${shipmentDetails.camion ?: "SIN CAMIÓN"} - ${shipmentDetails.descripcionCamion ?: "Descripción no disponible"}"
                            }

                            Text(
                                text = tituloPrincipal,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // --- Línea secundaria (ruta o almacenes) ---
                            val subtitulo = when {
                                // Movimiento entre almacenes
                                shipmentDetails.almOrigen != null && shipmentDetails.almDestino != null ->
                                    "Sucursal origen: ${shipmentDetails.almOrigen} • Sucursal destino: ${shipmentDetails.almDestino}"

                                // Ruta normal
                                else -> "${shipmentDetails.ruta ?: "RUTA"} - ${shipmentDetails.routeName}"
                            }

                            Text(
                                text = subtitulo,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF666666)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    Modifier, 1.dp,
                    Color(0xFFF0F0F0)
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Badge de estado
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (shipmentDetails.estadoID == 3) Color(0xFFE8F5E8) else Color(0xFFFEE9D7),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = when (shipmentDetails.estadoID) {
                                2 -> "En Proceso"
                                3 -> "Completado"
                                else -> "Estado ${shipmentDetails.status}"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (shipmentDetails.estadoID == 3) Color(0xFF26945A) else Color(0xFFFA944D)
                        )
                    }

                    // Indicador adicional
                    Icon(
                        imageVector = if (shipmentDetails.estadoID == 3) Icons.Default.CheckCircle else Icons.Default.Autorenew,
                        contentDescription = "Estado",
                        tint = if (shipmentDetails.estadoID == 3) Color(0xFF26945A) else Color(0xFFFA944D),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}