package org.example.project.ui.home.tabs.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.example.project.ui.home.tabs.admin.Components.AdminActionCard
import org.example.project.ui.home.tabs.admin.Components.StatItem
import org.example.project.ui.home.tabs.admin.Components.getAdminActions
import org.koin.compose.viewmodel.koinViewModel

// ui/admin/AdminScreen.kt
@Composable
fun AdminScreen(navController: NavHostController) {
    val adminViewModel = koinViewModel<AdminViewModel>()
    val state by adminViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(16.dp)
    ) {
        // --- Header ---
        AdminHeader()

        // --- Estadísticas rápidas ---
//        AdminStatsCard()

        // --- Acciones principales ---
        AdminActionsSection(navController)
    }
}

@Composable
private fun AdminHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Column {
            Text(
                "Panel de Administración",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                "Gestiona los recursos del sistema",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF546E7A)
            )
        }
    }
}

@Composable
private fun AdminStatsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()  // ← Esto es importante
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                count = "15",
                label = "Usuarios activos",
                icon = Icons.Default.People,
                color = Color(0xFF4CAF50)
            )
            StatItem(
                count = "8",
                label = "Rutas hoy",
                icon = Icons.Default.Route,
                color = Color(0xFF2196F3)
            )
            StatItem(
                count = "23",
                label = "Fotos hoy",
                icon = Icons.Default.PhotoCamera,
                color = Color(0xFF9C27B0)
            )
        }
    }
}

@Composable
private fun AdminActionsSection(navController: NavHostController) {
    Column(

    ) {
        Text(
            "Acciones rápidas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF37474F),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getAdminActions(navController).size) { index ->
                val action = getAdminActions(navController)[index]
                AdminActionCard(action = action)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}