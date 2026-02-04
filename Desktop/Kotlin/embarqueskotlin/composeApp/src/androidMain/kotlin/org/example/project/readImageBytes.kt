package org.example.project

import android.content.Context
import android.net.Uri
import android.util.Base64
import java.io.File

fun readImageBytes(context: Context, uriString: String): ByteArray? {
    val uri = Uri.parse(uriString)
    return context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
}

//fun convertPathToBase64(path: String): String {
//    return try {
//        val file = File(path)
//        if (file.exists()) {
//            val bytes = file.readBytes()
//            Base64.encodeToString(bytes, Base64.NO_WRAP)
//        } else ""
//    } catch (e: Exception) {
//        ""
//    }
//}