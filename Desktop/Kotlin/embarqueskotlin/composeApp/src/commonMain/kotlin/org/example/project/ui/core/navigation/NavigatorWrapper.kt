package org.example.project.ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.ui.core.navigation.Routes
import org.example.project.ui.home.HomeScreen
import org.example.project.ui.home.tabs.login.LoginScreen

@Composable
fun NavigationWrapper() {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routes.Login.route) {
        composable(route = Routes.Login.route) {
            LoginScreen(navController = mainNavController)
        }
        composable(route = Routes.Home.route) {
            HomeScreen()
        }
    }
}