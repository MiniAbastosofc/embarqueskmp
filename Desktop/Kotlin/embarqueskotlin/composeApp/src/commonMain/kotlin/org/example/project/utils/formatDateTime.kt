@file:OptIn(ExperimentalTime::class)

package org.example.project.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

fun formatDateTime(isoString: String): String {
    return try {
        val instant = Instant.parse(isoString)
        val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
        val day = localDateTime.dayOfMonth
        val month = when (localDateTime.monthNumber) {
            1 -> "enero"
            2 -> "febrero"
            3 -> "marzo"
            4 -> "abril"
            5 -> "mayo"
            6 -> "junio"
            7 -> "julio"
            8 -> "agosto"
            9 -> "septiembre"
            10 -> "octubre"
            11 -> "noviembre"
            12 -> "diciembre"
            else -> ""
        }
        val hour = localDateTime.hour
        val minute = localDateTime.minute.toString().padStart(2, '0')
        val amPm = if (hour < 12) "AM" else "PM"
        val displayHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour

        "$day de $month, $displayHour:$minute $amPm"
    } catch (e: Exception) {
        isoString // Fallback a la fecha original si hay error
    }
}

fun String.toFormattedDate(): String = formatDateTime(this)