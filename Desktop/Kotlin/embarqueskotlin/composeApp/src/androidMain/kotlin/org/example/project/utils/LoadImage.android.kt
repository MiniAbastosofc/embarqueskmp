package org.example.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
actual fun LoadImage(
    uri: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    // Usamos el composable AsyncImage de Coil
    AsyncImage(
        model = uri, // Coil sabe cómo manejar URIs de archivo, URIs de contenido, URLs, etc.
        contentDescription = "Imagen capturada",
        modifier = modifier,
        contentScale = contentScale,
        // Puedes añadir un placeholder o un error painter si lo deseas
        // placeholder = painterResource(R.drawable.placeholder),
        // error = painterResource(R.drawable.error_image),
    )
}