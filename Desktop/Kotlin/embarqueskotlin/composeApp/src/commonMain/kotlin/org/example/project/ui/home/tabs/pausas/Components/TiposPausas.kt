package org.example.project.ui.home.tabs.pausas.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TipoPausas(
    seleccionado: String,
    onOpcionSelected: (String) -> Unit
) {
    val opciones = listOf(
        TipoPausaData("Técnicas", Icons.Default.Build),
        TipoPausaData("Climáticas", Icons.Default.Cloud),
        TipoPausaData("Personal", Icons.Default.Person),
        TipoPausaData("Comida", Icons.Default.DinnerDining),
        TipoPausaData("Otro", Icons.Default.MoreHoriz)
    )

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = "TIPO DE PAUSA",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Usamos FlowRow para que si no caben en una línea, bajen a la siguiente automáticamente
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            opciones.forEach { opcion ->
                val esSeleccionado = seleccionado == opcion.nombre

                // Botón personalizado
                Surface(
                    modifier = Modifier
                        .clickable() { onOpcionSelected(opcion.nombre) }
                        .height(45.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = if (esSeleccionado) Color(0xFF1173D1) else Color.White,
                    border = BorderStroke(
                        1.dp,
                        if (esSeleccionado) Color(0xFF1173D1) else Color.LightGray
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = opcion.icono,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = if (esSeleccionado) Color.White else Color.Gray
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = opcion.nombre,
                            color = if (esSeleccionado) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

// Clase de datos simple para las opciones
data class TipoPausaData(val nombre: String, val icono: ImageVector)