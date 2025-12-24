@file:OptIn(ExperimentalTime::class)

package org.example.project.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

fun String.formatToTime(): String {
    return try {
        // Parsear el string a Instant
        val instant = Instant.parse(this)

        // Convertir a la zona horaria local
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        // Formatear a 12 horas con AM/PM
        val hour = localDateTime.hour
        val minute = localDateTime.minute.toString().padStart(2, '0')

        val (formattedHour, amPm) = if (hour == 0) {
            "12" to "AM"
        } else if (hour < 12) {
            hour.toString() to "AM"
        } else if (hour == 12) {
            "12" to "PM"
        } else {
            (hour - 12).toString() to "PM"
        }

        "$formattedHour:$minute $amPm"
    } catch (e: Exception) {
        // En caso de error, devolver el string original o un valor por defecto
        this
    }
}