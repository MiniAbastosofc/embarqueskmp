package org.example.project.domain

import org.example.project.data.remote.request.IniciarPausaRequest
import org.example.project.data.remote.response.VerificarPausaData
import org.example.project.domain.model.IniciarPausaModel

interface RepositoryPausas {
    suspend fun iniciarPausaRepository(request: IniciarPausaRequest): Result<IniciarPausaModel>
    suspend fun verificarPausaActiva(embarqueId: Int): Result<VerificarPausaData>
    suspend fun finalizarPausaRepository(pausaId: Int, usuarioId: Int): Result<String>
}