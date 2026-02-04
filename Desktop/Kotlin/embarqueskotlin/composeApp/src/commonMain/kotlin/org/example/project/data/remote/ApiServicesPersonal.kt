package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.response.PersonalBodegaResponse

class ApiServicesPersonal(private val client: HttpClient) {

    suspend fun obtenerPersonalBodega(): PersonalBodegaResponse {
        return client.get("/embarqueskotlin/personal-bodega") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}