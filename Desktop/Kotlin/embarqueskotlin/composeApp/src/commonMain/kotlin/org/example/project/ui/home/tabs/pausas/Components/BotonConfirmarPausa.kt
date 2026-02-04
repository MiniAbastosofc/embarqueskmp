package org.example.project.ui.home.tabs.pausas.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BotonConfirmarPausa(
    habilitado: Boolean = false,
    isLoading: Boolean = false,
    onClick: () -> Unit
) { // Puedes pasarle la lógica de validación aquí
    Column {
        Button( // Sugerencia: Usa Button en lugar de TextButton para que resalte más como acción principal
            onClick = onClick,
            enabled = habilitado && !isLoading, // Aquí controlas si se puede clickear o no
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1173D4), // Azul activo
                contentColor = Color.White,         // Texto blanco
                disabledContainerColor = Color(0xFFD1D5DB), // Gris azulado suave (contrasta bien)
                disabledContentColor = Color.White   // Texto blanco sobre el gris
            )
        ) {
            if (isLoading) {
                // Mostrar indicador de carga
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Iniciando...",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Confirmar Pausa",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}