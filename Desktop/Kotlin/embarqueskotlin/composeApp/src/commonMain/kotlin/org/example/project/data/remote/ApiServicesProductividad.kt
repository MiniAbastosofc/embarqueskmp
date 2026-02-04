package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.domain.model.ProductividadResponse
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ApiServicesProductividad(private val client: HttpClient) {
    suspend fun getProductividadSemanal(inicio: String, fin: String): ProductividadResponse {
        return client.get("/embarqueskotlin/productividad/semanal") {
            url {
                parameters.append("inicio", inicio)
                parameters.append("fin", fin)
            }
        }.body()
    }
}