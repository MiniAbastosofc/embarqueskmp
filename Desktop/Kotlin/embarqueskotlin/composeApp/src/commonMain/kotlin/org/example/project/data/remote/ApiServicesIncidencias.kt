package org.example.project.data.remote


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.response.IncidenciaPorFechaResponse

class ApiServicesIncidencias(private val client: HttpClient) {
    suspend fun incidenciasPorFecha(fecha: String): List<IncidenciaPorFechaResponse> {
        return client.get("/embarqueskotlin/incidencias/detalle") {
            // 1. Envía el parámetro de consulta (?fecha=2026-02-02)
            url {
                parameters.append("fecha", fecha)
            }
            // 2. KMP ya suele manejar esto si configuraste ContentNegotiation
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body() // Deserializa automáticamente a List<Incidencia>
    }
}