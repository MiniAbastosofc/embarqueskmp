package org.example.project.ui.core.navigation

import io.ktor.http.encodeURLParameter

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

    data object Incidencias : Routes("incidencias/{embarqueId}?imagenUri={imagenUri}") {
        fun withParams(embarqueId: String, imagenUri: String?): String {
            val encodedUri = imagenUri?.encodeURLParameter() ?: ""
            return "incidencias/$embarqueId?imagenUri=$encodedUri"
        }
    }

    data object ListaIncidencias : Routes("incidencias/lista")
    data object Pausas : Routes("pausas/{rutaId}/{usuarioId}") {
        fun createRoute(rutaId: String, usuarioId: Int): String {
            return "pausas/$rutaId/$usuarioId"
        }
    }


    data object Productividad : Routes("produtividad")
}