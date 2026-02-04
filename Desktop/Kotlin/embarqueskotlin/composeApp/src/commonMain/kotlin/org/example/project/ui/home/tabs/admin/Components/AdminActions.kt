package org.example.project.ui.home.tabs.admin.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import org.example.project.domain.model.AdminAction
import org.example.project.ui.core.navigation.Routes

fun getAdminActions(navController: NavHostController): List<AdminAction> {
    return listOf(
        AdminAction(
            title = "Crear Usuario",
            description = "Agregar nuevo usuario al sistema",
            icon = Icons.Default.PersonAdd,
            color = Color(0xFF4CAF50)
        ) {
            navController.navigate(Routes.CrearUsuario.route)
        },
//        AdminAction(
//            title = "Gestión de Usuarios",
//            description = "Ver y editar usuarios existentes",
//            icon = Icons.Default.ManageAccounts,
//            color = Color(0xFF2196F3)
//        ) {
//            navController.navigate(Routes.CrearUsuario.route)
//        },
//        AdminAction(
//            title = "Reportes",
//            description = "Generar reportes del sistema",
//            icon = Icons.Default.Assessment,
//            color = Color(0xFF9C27B0)
//        ) {
//            navController.navigate("reportes")
//        },
//        AdminAction(
//            title = "Configuración",
//            description = "Ajustes del sistema",
//            icon = Icons.Default.Settings,
//            color = Color(0xFFFF9800)
//        ) {
//            navController.navigate("configuracion")
//        },
        AdminAction(
            title = "Productividad",
            description = "Modulo para reporte de productividad",
            icon = Icons.Default.TrendingUp,
            color = Color(0xFFF44336)
        ) {
            navController.navigate(Routes.Productividad.route)
        },
        AdminAction(
            title = "Logs",
            description = "Registros del sistema",
            icon = Icons.Default.History,
            color = Color(0xFF607D8B)
        ) {
            navController.navigate(Routes.Historial.route)
        },
        AdminAction(
            title = "Incidencias",
            description = "Modulo para visualizacion de Incidencias",
            icon = Icons.Default.Warning,
            color = Color(0xFFF44336)
        ) {
            navController.navigate(Routes.ListaIncidencias.route)
        },
    )
}