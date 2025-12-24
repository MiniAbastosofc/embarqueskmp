package org.example.project.data.remote

import java.io.File

internal actual fun getFileBytes(filePath: String): ByteArray {
    println("🔍 getFileBytes - Ruta recibida: $filePath")

    try {
        // Intentar diferentes formas de leer el archivo
        val realPath = when {
            filePath.startsWith("file://") -> {
                filePath.removePrefix("file://")
            }
            filePath.startsWith("content://") -> {
                // Para content URIs, necesitamos usar ContentResolver
                getFilePathFromContentUri(android.net.Uri.parse(filePath))
            }
            else -> filePath
        }

        println("🔍 getFileBytes - Ruta procesada: $realPath")

        val file = File(realPath)
        println("📁 Archivo existe: ${file.exists()}")

        if (!file.exists()) {
            throw Exception("El archivo no existe en: $realPath")
        }

        println("📁 Tamaño del archivo: ${file.length()} bytes")

        val bytes = file.readBytes()
        println("✅ getFileBytes - Bytes leídos exitosamente: ${bytes.size} bytes")
        return bytes

    } catch (e: Exception) {
        println("❌ getFileBytes - Error: ${e.message}")
        throw e
    }
}

private fun getFilePathFromContentUri(contentUri: android.net.Uri): String {
    // Esta función es más compleja y requiere ContentResolver
    // Por ahora, lanzamos una excepción
    throw Exception("URIs content:// no soportadas temporalmente. Usa file://")
}