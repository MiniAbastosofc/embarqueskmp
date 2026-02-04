package org.example.project.ui.home.tabs.productividad

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
actual fun PlatformDatePicker(
    show: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    if (show) {
        // Estado del picker (Material 3)
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton (onClick = {
                    // Convertimos los milisegundos seleccionados a formato String
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.of("UTC"))
                            .toLocalDate()
                        onDateSelected(date.toString()) // Retorna YYYY-MM-DD
                    }
                    onDismiss()
                }) {
                    Text("Aceptar", color = Color(0xFF6200EE)) // Color del botón
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            // PERSONALIZACIÓN DE COLORES DEL CONTENEDOR
//            colors = DatePickerDialogDefaults.colors(
//                containerColor = Color.White,
//            ),
            shape = RoundedCornerShape(28.dp) // Bordes más redondeados y modernos
        ) {
            DatePicker(
                state = datePickerState,
                // PERSONALIZACIÓN DE COLORES INTERNOS
                colors = DatePickerDefaults.colors(
                    titleContentColor = Color.Black,
                    headlineContentColor = Color(0xFF6200EE), // Color de la fecha arriba
                    selectedDayContainerColor = Color(0xFF6200EE), // Círculo de selección
                    selectedDayContentColor = Color.White,
                    todayContentColor = Color(0xFF6200EE),
                    todayDateBorderColor = Color(0xFF6200EE)
                )
            )
        }
    }
}