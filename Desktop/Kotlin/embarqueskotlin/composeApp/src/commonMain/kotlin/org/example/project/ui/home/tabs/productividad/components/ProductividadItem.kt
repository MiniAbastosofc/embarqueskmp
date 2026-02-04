package org.example.project.ui.home.tabs.productividad.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.ProductividadModel

@Composable
fun ProductividadItem(item: ProductividadModel, posicion: Int) {
    // Definimos el color según el lugar
    val colorMedalla = when (posicion) {
        0 -> Color(0xFFFFD700) // Oro
        1 -> Color(0xFFC0C0C0) // Plata
        2 -> Color(0xFFCD7F32) // Bronce
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicador de posición o medalla
            Box(
                modifier = Modifier.size(40.dp).background(colorMedalla, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (posicion < 3) {
                    Icon(Icons.Default.Star, "Top", tint = Color.White)
                } else {
                    Text("${posicion + 1}", fontWeight = FontWeight.Bold)
                }
            }

            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(item.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Tonelaje: ${item.tonelaje} t", style = MaterialTheme.typography.bodySmall)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.eficiencia}",
                    color = Color(0xFF1173D4),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
                Text("Tons/Hr", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}