package org.example.project.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.example.project.data.UserDataManager
import org.example.project.ui.core.navigation.Routes
import org.example.project.ui.core.navigation.bottomnavigation.BottomBarItem
import org.example.project.ui.core.navigation.bottomnavigation.BottomBarItem.*
import org.example.project.ui.core.navigation.bottomnavigation.NavigationBottomWrapper

@Composable
fun HomeScreen() {
    val items = listOf(
        Rutas(),
//        Sucursales(),
        Historial(), Admin()
    )
    val navController = rememberNavController()
    val user = UserDataManager.getCurrentUser()
    val userRole = user?.RolID ?: -1

    val accessibleItems = getAccessibleBottomBarItems(userRole)

    Scaffold(bottomBar = { BottomNavigation(accessibleItems, navController) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavigationBottomWrapper(navController)
        }
    }
}

@Composable
fun BottomNavigation(items: List<BottomBarItem>, navController: NavHostController) {
    val navBarStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBarStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = item.icon,
                label = { Text(item.title) },
                onClick = {
                    navController.navigate(route = item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            )
        }
    }
}

private fun getAccessibleBottomBarItems(userRole: Int): List<BottomBarItem> {
    return buildList {
        // Rutas - Disponible para rol 1 y 2
        if (userRole == 1 || userRole == 2) {
            add(Rutas())
        }

        // Historial - Solo rol 1
        if (userRole == 1) {
            add(Historial())
        }

        // Admin - Solo rol 1
        if (userRole == 1) {
            add(Admin())
        }
    }
}