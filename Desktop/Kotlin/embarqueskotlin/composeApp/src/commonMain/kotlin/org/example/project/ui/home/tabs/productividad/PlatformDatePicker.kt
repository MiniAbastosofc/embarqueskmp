package org.example.project.ui.home.tabs.productividad

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformDatePicker(
    show: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
)