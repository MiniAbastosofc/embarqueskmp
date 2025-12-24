package org.example.project.ui.core.navigation

sealed class Routes(val route: String) {
    data object Login : Routes("login")
    data object Home : Routes("home")
    data object Rutas : Routes("rutas")
    data object Sucursales : Routes("sucursales")
    data object Historial : Routes("Historial")
    data object Admin : Routes("admin")
    data object CapturaRutas : Routes("captura-rutas/{rutaId}/{statusId}") {
        fun withId(id: Int, statusId: Int) = "captura-rutas/$id/$statusId"
    }

    data object DetallesHistorial : Routes("detalle-historial/{id}") {
        fun withId(id: Int) = "detalle-historial/$id"
    }

    data object CrearUsuario : Routes("crear-usuario")
    data object CapturaSucursales : Routes("captura-suc/{id}/{statusid}") {
        fun withId(id: Int, statusId: Int) = "captura-suc/$id/$statusId"
    }

    data object Incidencias : Routes("incidencias?imagenUri={imagenUri}") {
        // Función para construir la ruta con imagen
        fun withImage(imagenUri: String?) =
            "incidencias?imagenUri=${imagenUri ?: ""}"

        // Mantén tu función withId si la necesitas
        fun withId(id: Int) = "incidencias/$id"
    }
}