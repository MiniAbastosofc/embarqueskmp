package org.example.project.data

import org.example.project.data.remote.ApiServicePausas
import org.example.project.data.remote.request.FinalizarPausaEmbarquesRequest
import org.example.project.data.remote.request.IniciarPausaEmbarqueRequest
import org.example.project.data.remote.request.IniciarPausaRequest
import org.example.project.data.remote.response.VerificarPausaData
import org.example.project.data.remote.response.toDomain
import org.example.project.domain.RepositoryPausas
import org.example.project.domain.model.IniciarPausaModel


class RespositoryImplPausa(val api: ApiServicePausas) : RepositoryPausas {
    override suspend fun iniciarPausaRepository(
        request: IniciarPausaRequest
    ): Result<IniciarPausaModel> {
        return try {
            val apiRequest = IniciarPausaEmbarqueRequest(
                EmbarqueID = request.embarqueID,
                ExtensionId = request.extensionId,
                UsuarioID = request.usuarioID,
                MotivoPausa = request.motivoPausa,
                TipoPausa = request.tipoPausa
            )

            val apiResponse = api.iniciarPausaEmbarques(apiRequest)

            // EVALUAMOS EL CAMPO SUCCESS DEL JSON
            if (apiResponse.success) {
                // Si es true, mapeamos el objeto 'data' al dominio
                Result.success(apiResponse.toDomain())
            } else {
                // Si es false, capturamos el mensaje de "Pausa activa"
                val mensajeError = apiResponse.error ?: apiResponse.message ?: "Error desconocido"
                Result.failure(Exception(mensajeError))
            }
        } catch (e: Exception) {
            // Errores de red o de serialización
            Result.failure(e)
        }
    }

    override suspend fun verificarPausaActiva(embarqueId: Int): Result<VerificarPausaData> {
        return try {
            val response = api.verificarPausaActiva(embarqueId)
            // En este caso, el servidor responde success: true incluso si no hay pausa (data.existeRegistro = false)
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun finalizarPausaRepository(pausaId: Int, usuarioId: Int): Result<String> {
        return try {
            // Creamos el objeto que espera Node.js
            val request = FinalizarPausaEmbarquesRequest(
                PausaId = pausaId,
                UsuarioId = usuarioId
            )
            val response = api.finalizarPausaEmbarque(request)

            if (response.success) {
                Result.success(response.message ?: "Pausa finalizada")
            } else {
                Result.failure(Exception(response.error ?: "Error al finalizar"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}