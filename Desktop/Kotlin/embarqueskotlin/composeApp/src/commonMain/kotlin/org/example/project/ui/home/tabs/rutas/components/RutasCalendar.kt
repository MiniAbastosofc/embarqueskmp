@file:OptIn(ExperimentalTime::class)

package org.example.project.ui.home.tabs.rutas.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.time.ExperimentalTime
import kotlinx.datetime.*
import kotlin.time.Clock

@Composable
fun RutasCalendar(
    fechaSeleccionada: String?,
    onDateSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showInvalidDateDialog by remember { mutableStateOf(false) }

    // Calcular límites usando las funciones correctas
    val (today, threeWeeksAgo) = remember {
        // Usar kotlin.time.Clock en lugar del deprecated
        val todayDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        // Usar minus con DateTimeUnit en lugar de DatePeriod
        val threeWeeksBack = todayDate.minus(21, DateTimeUnit.DAY) // 3 semanas = 21 días
        todayDate to threeWeeksBack
    }

    // Botón que activa el calendario
    Button(
        onClick = { showDatePicker = true },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6)),
        modifier = modifier
    ) {
        Icon(Icons.Default.CalendarToday, contentDescription = "Calendario")
        Spacer(Modifier.width(8.dp))
        Text(text = fechaSeleccionada ?: "Seleccionar fecha")
    }

    // Diálogo del calendario
    if (showDatePicker) {
        // Mover datePickerState aquí dentro para que sea accesible
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = fechaSeleccionada?.let { parseFecha(it) },
            yearRange = IntRange(threeWeeksAgo.year, today.year),
            initialDisplayMode = DisplayMode.Picker
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.UTC).date

                            if (selectedDate >= threeWeeksAgo && selectedDate <= today) {
                                val formatted = buildString {
                                    append(selectedDate.year.toString().padStart(4, '0'))
                                    append(selectedDate.monthNumber.toString().padStart(2, '0'))
                                    append(selectedDate.dayOfMonth.toString().padStart(2, '0'))
                                }
                                onDateSelected(formatted)
                                showDatePicker = false
                            } else {
                                showInvalidDateDialog = true
                            }
                        } ?: run {
                            showDatePicker = false
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    onDateSelected(null)
                }) {
                    Text("Cancelar")
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                headline = null,
                showModeToggle = false,
                title = null,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                    selectedDayContainerColor = Color(0xFFE3F2FD),
                    todayDateBorderColor = Color(0xFFE3F2FD)
                ),
            )
        }
    }

    // Diálogo para fecha inválida
    if (showInvalidDateDialog) {
        AlertDialog(
            onDismissRequest = { showInvalidDateDialog = false },
            title = { Text("Fecha no válida") },
            text = {
                Text("Solo puedes seleccionar fechas desde ${formatDateForDisplay(threeWeeksAgo)} hasta ${formatDateForDisplay(today)}")
            },
            confirmButton = {
                TextButton(onClick = { showInvalidDateDialog = false }) {
                    Text("Entendido")
                }
            }
        )
    }
}

// Función helper para parsear fecha desde String "YYYYMMDD" a milisegundos
private fun parseFecha(fecha: String): Long? {
    return try {
        if (fecha.length == 8) {
            val year = fecha.substring(0, 4).toInt()
            val month = fecha.substring(4, 6).toInt()
            val day = fecha.substring(6, 8).toInt()

            val localDate = LocalDate(year, month, day)
            localDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}

// Función helper para formatear fecha para display
private fun formatDateForDisplay(date: LocalDate): String {
    return "${date.dayOfMonth}/${date.monthNumber}/${date.year}"
}