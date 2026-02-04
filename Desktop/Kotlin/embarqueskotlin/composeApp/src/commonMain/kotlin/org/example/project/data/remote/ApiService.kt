package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import kotlinx.serialization.json.Json
import org.example.project.data.remote.request.CrearIncidenciaRequest
import org.example.project.data.remote.response.AgregarFotoResponse
import org.example.project.data.remote.response.CompletarEmbarqueResponse
import org.example.project.data.remote.response.CrearIncidenciaResponse
import org.example.project.data.remote.response.CrearUsuarioResponse
import org.example.project.data.remote.response.IniciarRutaPayload
import org.example.project.data.remote.response.IniciarRutaResponse
import org.example.project.data.remote.response.LoginResponse
import org.example.project.data.remote.response.RutaDetallesFechaResponse
import org.example.project.data.remote.response.RutaDetallesUnidoResponse
//import org.example.project.data.remote.response.RutasResponse
import org.example.project.data.remote.response.RutasResponseDto
import org.example.project.data.remote.response.TipoIncidenciasResponse
import org.example.project.domain.model.TipoIncidenciasModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class ApiService(private val client: HttpClient) {
    suspend fun loginAutenticacion(Usuario: String, PasswordHash: String): LoginResponse {
        // 1. Crea el objeto de solicitud esperado por la API
        val loginRequest = LoginRequest(Usuario = Usuario, PasswordHash = PasswordHash)
        // 2. Realiza la petición POST correctamente
        val response = client.post("/embarqueskotlin/login") {
            contentType(ContentType.Application.Json) // Indica que enviamos JSON
            setBody(loginRequest) // Ktor serializa este objeto a JSON automáticamente
        }
        // 3. Devuelve el cuerpo de la respuesta, Ktor lo deserializa a LoginResponse
        return response.body<LoginResponse>()
    }

    suspend fun getRutasPorfecha(fecha: String): RutasResponseDto {
        val respuesta = client.get("/embarqueskotlin/embarques") {
            url { parameters.append("fecha", fecha) }
        }.body<RutasResponseDto>()


        return respuesta
    }

    suspend fun iniciarRuta(
        EmbarqueID: Int,
        UsuarioID: Int?,
        tonelaje: Double?,
        checadorId: Int,
        estibadorId: Int
    ): IniciarRutaResponse {
        return client.post("/embarqueskotlin/embarques/iniciar") {
            val payload = IniciarRutaPayload(
                EmbarqueID = EmbarqueID,
                UsuarioID = UsuarioID,
                Peso = tonelaje,
                ChecadorID = checadorId,
                EstibadorID = estibadorId
            )
            contentType(ContentType.Application.Json)
            setBody(payload)
        }.body()
    }

    suspend fun getRoutesByDate(date: String): List<RutaDetallesFechaResponse> {
        return client.get("/embarqueskotlin/embarques/rutas-fec") {
            url {
                parameters.append("fecha", date)
            }
        }.body()
    }

    // Endpoint 2: /embarques/detalle/115
    suspend fun getShipmentDetails(shipmentId: Int): RutaDetallesUnidoResponse {
        return client.get("/embarqueskotlin/embarques/detalle/$shipmentId").body()
    }

    suspend fun agregarFoto(
        embarqueID: Int,
        comentario: String?,
        porcentajeAvance: Int,
        usuarioID: Int?,
        filePath: String
    ): AgregarFotoResponse {
        println("🔍 API CALL - agregarFoto iniciando...")
        println("📦 Datos a enviar:")
        println("   - embarqueID: $embarqueID")
        println("   - comentario: $comentario")
        println("   - porcentajeAvance: $porcentajeAvance")
        println("   - usuarioID: $usuarioID")
        println("   - filePath: $filePath")

        return try {
            // Crear timestamp único sin depender de System.currentTimeMillis()
            val timestamp = (embarqueID * 1000 + (0..999).random()).toString()

            val response = client.submitFormWithBinaryData(
                url = "/embarqueskotlin/embarques/foto",
                formData = formData {
                    append("embarqueID", embarqueID.toString())
                    append("porcentajeAvance", porcentajeAvance.toString())
                    append("usuarioID", usuarioID.toString())
                    comentario?.let {
                        append("comentario", it)
                    }
                    appendInput("imagen", Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"foto_${embarqueID}_${timestamp}.jpg\""
                        )
                    }) {
                        buildPacket {
                            val fileBytes = getFileBytes(filePath)
                            println("📁 Bytes leídos: ${fileBytes.size} bytes")
                            writeFully(fileBytes)
                        }
                    }
                }
            ).body<AgregarFotoResponse>()

            println("✅ API CALL - Respuesta exitosa recibida")
            response
        } catch (e: Exception) {
            println("❌ API CALL - Error: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun completarEmbarque(embarqueID: String, usuarioID: Int): CompletarEmbarqueResponse {
        // Convertir a Int antes de crear la request
        val embarqueIdInt = embarqueID.toIntOrNull()
            ?: throw IllegalArgumentException("EmbarqueID no es numérico: $embarqueID")

        val response = client.put("/embarqueskotlin/embarques/completar") {
            val payload = CompletarEmbarqueRequest(
                embarqueID = embarqueIdInt,  // ✅ Ahora es Int
                usuarioID = usuarioID
            )
            contentType(ContentType.Application.Json)
            setBody(payload)
        }

        val rawResponse = response.body<String>()
        println("🔍 RESPUESTA CRUDA DEL SERVIDOR: '$rawResponse'")
        println("🔍 STATUS: ${response.status}")

        return Json.decodeFromString<CompletarEmbarqueResponse>(rawResponse)
    }

    suspend fun crearUsuario(request: CrearUsuarioRequest): CrearUsuarioResponse {
        return client.post("/embarqueskotlin/usuarios") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<CrearUsuarioResponse>()
    }

    suspend fun obtenerTipoIncidenciasApiService(): List<TipoIncidenciasResponse> {
        return client.get("/embarqueskotlin/incidencias/tipos").body()
    }

    suspend fun crearIncidenciaApiService(
        request: CrearIncidenciaRequest
    ): CrearIncidenciaResponse {
        return client.post("/embarqueskotlin/incidencias/crear") {
            setBody(MultiPartFormDataContent(
                formData {
                    append("EmbarqueId", request.EmbarqueId.toString())
                    append("IdTipoIncidencia", request.IdTipoIncidencia.toString())
                    append("UsuarioRegistroId", request.UsuarioRegistroId.toString())
                    append("Cantidad", (request.Cantidad ?: 1).toString())
                    append("Descripcion", request.Descripcion ?: "")
                    append("Resolucion", request.Resolucion ?: "")

                    // ESTA PARTE ES CRÍTICA:
                    append("Evidencia", request.Evidencia, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpeg")
                        // Multer necesita que el parámetro 'name' sea "Evidencia"
                        // Ktor lo añade automáticamente si usas la llave en el append,
                        // pero el filename es OBLIGATORIO para que Multer lo vea como 'file'.
                        append(HttpHeaders.ContentDisposition, "filename=\"evidencia.jpg\"")
                    })
                }
            ))
        }.body()
    }

}

internal expect fun getFileBytes(filePath: String): ByteArray