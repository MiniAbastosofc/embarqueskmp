package org.example.project.ui.core.navigation.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddRoad
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import org.example.project.ui.core.navigation.Routes

sealed class BottomBarItem {
    abstract val route: String
    abstract val title: String
    abstract val icon: @Composable () -> Unit

    data class Rutas(
        override val route: String = Routes.Rutas.route,
        override val title: String = "Rutas",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.AddRoad, "")
        }
    ) : BottomBarItem()

//    data class Sucursales(
//        override val route: String = Routes.Sucursales.route,
//        override val title: String = "Sucursales",
//        override val icon: @Composable () -> Unit = {
//            Icon(imageVector = Icons.Default.Storefront, "")
//        }
//    ) : BottomBarItem()

    data class Historial(
        override val route: String = Routes.Historial.route,
        override val title: String = "Historial",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.History, "")
        }
    ) : BottomBarItem()

    data class Admin(
        override val route: String = Routes.Admin.route,
        override val title: String = "Admin",
        override val icon: @Composable () -> Unit = {
            Icon(imageVector = Icons.Default.ManageAccounts, "")
        }
    ) : BottomBarItem()
}