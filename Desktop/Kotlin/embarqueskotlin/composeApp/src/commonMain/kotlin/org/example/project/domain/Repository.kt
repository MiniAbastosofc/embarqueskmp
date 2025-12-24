package org.example.project.domain

import org.example.project.data.remote.CrearUsuarioRequest
import org.example.project.data.remote.request.CrearIncidenciaRequest
import org.example.project.data.remote.response.CrearUsuarioResponse
import org.example.project.domain.model.CompletarEmbarqueModel
import org.example.project.domain.model.CrearIncidenciaModel
import org.example.project.domain.model.CrearUsuarioModel
import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.domain.model.LoginModel
import org.example.project.domain.model.RutaDetallesModel
import org.example.project.domain.model.RutasModel
import org.example.project.domain.model.TipoIncidenciasModel

interface Repository {
    suspend fun loginAuth(Usuario: String, PasswordHash: String): LoginModel
    suspend fun getRutasRepository(fecha: String): List<RutasModel>
    suspend fun iniciarRutasRepository(EmbarqueID: Int, UsuarioID: Int?): String
    suspend fun getShipmentDetailsByDateRepository(date: String): List<RutaDetallesModel>
    suspend fun getShipmentDetailsByIdRepository(shipmentId: Int): DetalleEmbarqueCompleto

    suspend fun agregarFotoRepository(
        embarqueID: Int,
        comentario: String?,
        porcentajeAvance: Int,
        usuarioID: Int?,
        filePath: String
    ): String

    suspend fun completarEmbarqueRepository(
        EmbarqueID: String,
        UsuarioID: Int
    ): CompletarEmbarqueModel

    suspend fun crearUsuario(request: CrearUsuarioRequest): Result<CrearUsuarioModel>

    suspend fun obtenerTipoIncidenciasApiService(): List<TipoIncidenciasModel>

    suspend fun crearincidenciaApiService(request: CrearIncidenciaRequest): CrearIncidenciaModel

}