package org.example.project.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

data class AdminAction(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)