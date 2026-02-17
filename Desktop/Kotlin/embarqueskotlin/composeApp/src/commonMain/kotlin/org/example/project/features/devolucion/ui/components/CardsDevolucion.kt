package org.example.project.features.devolucion.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.core.navigation.Routes

val AzulOscuro = Color(0xFF0056EA)
val VerdeExito = Color(0xFF16A34A)
val GrisTexto = Color(0xFFD97706) // Este es un tono naranja/ocre según el código

@Composable
fun CardsDevolucion(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable {
                    val route = Routes.DevolucionDetalle.createRoute("01")
                    navController.navigate(route)
                },
            //elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            // Fondo con opacidad basado en tu color azul (0.1f es el 10% de opacidad)
            colors = CardDefaults.cardColors(
                containerColor = AzulOscuro.copy(alpha = 0.08f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lado izquierdo: Icono y Textos
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Fondo circular para el icono
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(AzulOscuro.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = null,
                            tint = AzulOscuro, // Tu color 0056EA
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Camión 01",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F2937) // Gris oscuro para legibilidad
                        )
                        Text(
                            text = "Ruta 123",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.DarkGray
                        )
                    }
                }

                // Lado derecho: Status con el color verde 16A34A
                Surface(
                    color = VerdeExito.copy(alpha = 0.15f), // Fondo verde tenue
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "ENTREGADO",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = VerdeExito, // Tu color 16A34A
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
