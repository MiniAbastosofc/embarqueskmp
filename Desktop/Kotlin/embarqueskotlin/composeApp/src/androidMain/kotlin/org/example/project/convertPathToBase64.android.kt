package org.example.project

import android.util.Base64
import java.io.File
import android.net.Uri

actual fun convertPathToBase64(path: String): String {
    return try {
        // 1. Limpiar el path: convertimos la cadena "file://..." a un Path real
        val cleanPath = if (path.startsWith("file://")) {
            Uri.parse(path).path // Esto quita el "file://" y deja solo "/storage/..."
        } else {
            path
        }

        val file = File(cleanPath ?: "")

        if (file.exists()) {
            val bytes = file.readBytes()
            val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
            println("DEBUG ANDROID: ¡Éxito! Tamaño Base64: ${base64.length}")
            base64
        } else {
            println("DEBUG ANDROID: El archivo no existe en: $cleanPath")
            ""
        }
    } catch (e: Exception) {
        println("DEBUG ANDROID: Error en conversión: ${e.message}")
        ""
    }
}