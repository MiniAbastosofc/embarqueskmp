package org.example.project.data

import org.example.project.data.remote.ApiService
import org.example.project.data.remote.CrearUsuarioRequest
import org.example.project.data.remote.request.CrearIncidenciaRequest
import org.example.project.data.remote.response.AgregarFotoResponse
import org.example.project.data.remote.response.CompletarEmbarqueResponse
import org.example.project.data.remote.response.IniciarRutaResponse
import org.example.project.data.remote.response.toDomain
import org.example.project.data.remote.response.toDomainDetalles
import org.example.project.domain.Repository
import org.example.project.domain.model.CompletarEmbarqueModel
import org.example.project.domain.model.CrearIncidenciaModel
import org.example.project.domain.model.CrearUsuarioModel
import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.domain.model.LoginModel
import org.example.project.domain.model.RutaDetallesModel
import org.example.project.domain.model.RutasModel
import org.example.project.domain.model.TipoIncidenciasModel

class RepositoryImpl(
    val api: ApiService
) : Repository {

    override suspend fun loginAuth(Usuario: String, PasswordHash: String): LoginModel {

        val response = api.loginAutenticacion(Usuario, PasswordHash)
        // 1. Verificar si el campo 'user' es nulo
        if (response.user == null) {
            // Si es nulo, significa que hubo un problema de autenticación o error en el backend
            // Lanzamos una excepción con el mensaje que nos dio la API
            throw Exception(response.message ?: "Credenciales incorrectas o error desconocido.")
        }

        // 2. Si NO es nulo, procedemos con el mapeo a dominio
        return response.toDomain()
    }

    override suspend fun getRutasRepository(fecha: String): List<RutasModel> {
        return api.getRutasPorfecha(fecha).toDomain()
    }

    override suspend fun iniciarRutasRepository(
        EmbarqueID: Int,
        UsuarioID: Int?,
        tonelaje: Double?,
        checadorId: Int,
        estibadorId: Int
    ): String {
        val response: IniciarRutaResponse = api.iniciarRuta(
            EmbarqueID = EmbarqueID,
            UsuarioID = UsuarioID,
            tonelaje = tonelaje,
            checadorId = checadorId,
            estibadorId = estibadorId
        )
        val mensajeExito: String = response.message
        return mensajeExito
    }

    override suspend fun getShipmentDetailsByDateRepository(date: String): List<RutaDetallesModel> {
        return api.getRoutesByDate(date)
            .map { it.toDomainDetalles().toRutaDetallesModel() }
    }

    override suspend fun getShipmentDetailsByIdRepository(shipmentId: Int): DetalleEmbarqueCompleto {
        // Simplemente llamas a la función toDomain()
        return api.getShipmentDetails(shipmentId).toDomain()
    }

    override suspend fun agregarFotoRepository(
        embarqueID: Int,
        comentario: String?,
        porcentajeAvance: Int,
        usuarioID: Int?,
        filePath: String
    ): String {
        val response: AgregarFotoResponse = api.agregarFoto(
            embarqueID = embarqueID,
            comentario = comentario,
            porcentajeAvance = porcentajeAvance,
            usuarioID = usuarioID,
            filePath = filePath
        )
        val mensajeExito: String = response.message
        return mensajeExito
    }

    override suspend fun completarEmbarqueRepository(
        embarqueID: String,
        usuarioID: Int
    ): CompletarEmbarqueModel {
        val response: CompletarEmbarqueResponse = api.completarEmbarque(
            embarqueID = embarqueID,
            usuarioID = usuarioID
        )

        return response.toDomain()
    }

    override suspend fun crearUsuario(request: CrearUsuarioRequest): Result<CrearUsuarioModel> {
        return try {
            val response = api.crearUsuario(request)

            // Si la respuesta tiene user, fue exitosa
            if (response.user != null) {
                Result.success(response.toDomain())
            } else {
                // Si no tiene user, hubo un error
                Result.failure(Exception(response.message ?: "Error al crear usuario"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    override suspend fun obtenerTipoIncidenciasApiService(): List<TipoIncidenciasModel> {
        return api.obtenerTipoIncidenciasApiService()
            .map { it.toDomain() }
    }

    override suspend fun crearincidenciaApiService(
        request: CrearIncidenciaRequest
    ): CrearIncidenciaModel {

        val response = api.crearIncidenciaApiService(request)

        return CrearIncidenciaModel(
            NuevaIncidenciaId = response.data.NuevaIncidenciaID
        )
    }

}

fun DetalleEmbarqueCompleto.toRutaDetallesModel(): RutaDetallesModel {
    return RutaDetallesModel(
        id = info.embarqueID,
        routeName = info.nombreRuta ?: info.desAlmOrigen ?: "SIN NOMBRE",
        status = info.estado,
        percentage = info.porcentajeAvance,
        durationFormatted = info.duracionFormateada,
        photoCount = info.totalArticulos ?: 0,
        embarqueId = info.embarqueID,
        fechaEmbarque = info.fechaEmbarque,

        ruta = info.rutaID,
        descripcionRuta = info.nombreRuta,
        camion = info.camionID,
        descripcionCamion = info.nombreCamion,

        almOrigen = info.almOrigen,
        desAlmOrigen = info.desAlmOrigen,
        almDestino = info.almDestino,
        desAlmDestino = info.desAlmDestino,

        cantidadPedidos = info.cantidadPedidos,
        estadoID = info.estadoID,
        estado = info.estado,
        fechaInicio = info.fechaInicio,
        fechaCompletado = info.fechaCompletado,
        usuarioInicio = info.usuarioInicio,
        usuarioCompletado = info.usuarioCompletado,
        nombreUsuarioInicio = info.nombreUsuarioInicio,
        nombreUsuarioCompletado = info.nombreUsuarioCompletado,
        duracionMinutos = info.duracionMinutos ?: 0,
        fechaCreacion = info.fechaCreacion,
        usuarioCreacion = info.usuarioCreacion,

        fotos = listOf(this)
    )
}
