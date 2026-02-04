package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.example.project.data.remote.request.BuscarEmbarquePorIdRequest
import org.example.project.data.remote.response.BuscarEmbarquePorIdResponse

class ApiServicesEmbarques(private val client: HttpClient) {

    suspend fun buscarEmbarquePorId(id: Int): BuscarEmbarquePorIdResponse {
        // 1. Cambiamos a .get
        // 2. Metemos el id directamente en la ruta /buscar/232
        val response = client.get("/embarqueskotlin/embarques/buscar/$id") {
            contentType(ContentType.Application.Json)
        }

        val jsonString = response.bodyAsText()
        println("DEBUG API RESPONSE: $jsonString")

        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }.decodeFromString<BuscarEmbarquePorIdResponse>(jsonString)
    }
}