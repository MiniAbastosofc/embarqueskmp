package org.example.project.utils

import android.content.Context
import android.net.Uri
import android.util.Base64

actual fun convertirUriABase64Platform(uriString: String, context: Any): String {
    val androidContext = context as Context
    return try {
        val uri = Uri.parse(uriString)
        val bytes = androidContext.contentResolver.openInputStream(uri)?.readBytes()
        if (bytes != null) Base64.encodeToString(bytes, Base64.NO_WRAP) else ""
    } catch (e: Exception) {
        ""
    }
}