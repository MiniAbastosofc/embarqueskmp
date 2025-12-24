package org.example.project

import androidx.compose.runtime.Composable

//@Composable
//expect fun CameraView()

@Composable
expect fun CameraView(onImageCaptured: (String) -> Unit)