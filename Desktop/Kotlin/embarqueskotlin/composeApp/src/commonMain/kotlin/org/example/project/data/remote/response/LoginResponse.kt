package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import org.example.project.domain.model.LoginModel

@Serializable
data class LoginResponse(
    val message: String, // Coincide con la clave "message"
    val user: UserData? = null // Coincide con la clave "user"
) {
    // Función de mapeo al modelo de dominio.
    // Ahora mapea desde UserData en lugar de directamente desde LoginResponse
    fun toDomain(): LoginModel {
        return user?.toDomain()
            ?: throw IllegalStateException("Los datos de usuario estaban ausentes en la respuesta exitosa.")
    }
}

// Creamos una nueva clase para manejar los datos anidados del usuario
@Serializable
data class UserData(
    // Nota: Tu backend usa "UsuarioID" con D mayúscula, tu modelo usa "UsuarioId"
    // Asegúrate de que los nombres coincidan o usa @SerialName
    val UsuarioID: Int, // Coincide con "UsuarioID"
    val Nombre: String,
    val Apellido: String,
    val NumeroEmpleado: String,
    val Usuario: String,
    val RolID: Int,
    val NombreRol: String,
    val Activo: Boolean,
    val FechaCreacion: String
) {
    fun toDomain(): LoginModel {
        return LoginModel(
            UsuarioId = UsuarioID, // Mapeo aquí
            Nombre = Nombre,
            Apellido = Apellido,
            NumeroEmpleado = NumeroEmpleado,
            Usuario = Usuario,
            RolID = RolID,
            NombreRol = NombreRol,
            Activo = Activo,
            FechaCreacion = FechaCreacion
        )
    }
}