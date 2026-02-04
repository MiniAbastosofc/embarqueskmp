package org.example.project.utils

import android.content.Context
import android.net.Uri
import android.util.Base64

// Esta función vive en Android, por eso aquí sí reconoce Context y Uri
fun convertirUriABase64(uriString: String, context: Context): String {
    return try {
        val uri = Uri.parse(uriString)
        val inputStream = context.contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()

        if (bytes != null) {
            Base64.encodeToString(bytes, Base64.NO_WRAP)
        } else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}