package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.project.data.remote.request.FinalizarPausaEmbarquesRequest
import org.example.project.data.remote.request.IniciarPausaEmbarqueRequest
import org.example.project.data.remote.response.FinalizarPausaEmbarqueResponse
import org.example.project.data.remote.response.IniciarPausaResponse
import org.example.project.data.remote.response.VerificarPausaResponse


class ApiServicePausas(private val client: HttpClient) {

    suspend fun verificarPausaActiva(embarqueId: Int): VerificarPausaResponse {
        return client.get("/embarqueskotlin/pausas/existe/$embarqueId") {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }.body()
    }

    suspend fun iniciarPausaEmbarques(request: IniciarPausaEmbarqueRequest): IniciarPausaResponse {
        val response = client.post("/embarqueskotlin/pausas/iniciar") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.value != 200) {
            val errorBody = response.bodyAsText()
            println("ERROR DEL SERVIDOR (Pausa): $errorBody")
            // Opcional: podrías lanzar una excepción personalizada aquí
        }
        return response.body()
    }

    suspend fun finalizarPausaEmbarque(request: FinalizarPausaEmbarquesRequest): FinalizarPausaEmbarqueResponse {
        return client.post("/embarqueskotlin/pausas/finalizar") {
            contentType(ContentType.Application.Json)
            setBody(request) // Pasamos los datos reales aquí
        }.body()
    }
}