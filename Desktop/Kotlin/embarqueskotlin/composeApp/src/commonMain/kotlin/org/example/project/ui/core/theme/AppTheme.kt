package org.example.project.ui.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1173D4),
    onPrimary = Color.White,
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}