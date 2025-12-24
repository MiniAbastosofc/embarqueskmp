package org.example.project.ui.core.navigation.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.example.project.ui.core.navigation.Routes
import org.example.project.ui.home.tabs.admin.AdminScreen
import org.example.project.ui.home.tabs.admin.AdminUsuarios.AdminCrearUsuario
import org.example.project.ui.home.tabs.historial.HistorialDetalle
import org.example.project.ui.home.tabs.historial.HistorialScreen
import org.example.project.ui.home.tabs.incidencias.IncidenciasScreen
import org.example.project.ui.home.tabs.rutas.RutasScreen
import org.example.project.ui.home.tabs.login.LoginScreen
import org.example.project.ui.home.tabs.rutas.components.CapturaRutas
import org.example.project.ui.home.tabs.sucursales.SucursalesScreen
import org.example.project.ui.home.tabs.sucursales.components.CapturaSucursales

@Composable
fun NavigationBottomWrapper(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Rutas.route) {
        composable(route = Routes.Rutas.route) { RutasScreen(navController = navController) }
        composable(route = Routes.Sucursales.route) { SucursalesScreen(navController = navController) }
        composable(route = Routes.Historial.route) {
//            LoginScreen()
//            CapturaRutas()
            HistorialScreen(navController = navController)
//            IncidenciasScreen()
        }
        composable(route = Routes.Admin.route) { AdminScreen(navController = navController) }
        //Aqui empiezan las rutas que no estaran en el BottomBarItem
        composable(
            route = Routes.CapturaRutas.route,
            arguments = listOf(
                navArgument("rutaId") { type = NavType.StringType },
                navArgument("statusId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val rutaId = backStackEntry.arguments?.getString("rutaId")
            val statusId = backStackEntry.arguments?.getInt("statusId")
            CapturaRutas(navController = navController, rutaId = rutaId, statusId = statusId)
        }
        composable(
            route = Routes.DetallesHistorial.route, arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            HistorialDetalle(navController = navController, id = id)
        }
        composable(route = Routes.CrearUsuario.route) {
            AdminCrearUsuario(navController = navController)
        }

        //Routes de sucursal
        composable(route = Routes.CapturaSucursales.route) {
            CapturaSucursales(
                navController = navController,
//                sucursalesViewModel = TODO()
            )
        }
        composable(
            route = Routes.Incidencias.route,
            arguments = listOf(
                navArgument("imagenUri") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            // Obtén el parámetro de la ruta
            val imagenUri = backStackEntry.arguments?.getString("imagenUri")

            IncidenciasScreen(
                navController = navController,
                imagenUri = imagenUri
            )
        }
    }
}