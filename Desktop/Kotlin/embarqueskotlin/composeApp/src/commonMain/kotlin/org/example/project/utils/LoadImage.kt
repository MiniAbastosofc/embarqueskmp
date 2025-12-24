package org.example.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
expect fun LoadImage(
    uri: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
)