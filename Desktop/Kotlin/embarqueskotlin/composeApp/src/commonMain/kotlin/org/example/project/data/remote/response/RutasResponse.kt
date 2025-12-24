package org.example.project.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.project.domain.model.RutasModel

//@Serializable
//data class RutasResponse(
//    @SerialName("embarqueID") val embarqueID: Int,
//    @SerialName("fechaEmbarque") val fechaEmbarque: String,
//    @SerialName("ruta") val ruta: String,
//    @SerialName("descripcionRuta") val descripcionRuta: String,
//    @SerialName("camion") val camion: String,
//    @SerialName("descripcionCamion") val descripcionCamion: String,
//    @SerialName("cantidadPedidos") val cantidadPedidos: Int,
//    @SerialName("estadoID") val estadoID: Int,
//    @SerialName("porcentajeAvance") val porcentajeAvance: Int
//) {
//    // Función de mapeo a modelo de dominio
//    fun toDomain(): RutasModel {
//        return RutasModel(
//            id = embarqueID,
//            fecha = fechaEmbarque,
//            ruta = ruta,
//            rutaDescripcion = descripcionRuta,
//            camion = camion,
//            camionDescripcion = descripcionCamion,
//            cantidadPedidos = cantidadPedidos,
//            statusId = estadoID,
//            avance = porcentajeAvance
//        )
//    }
//}
// Este endpoint devuelve una lista, no un solo objeto contenedor como el login
// No necesitamos una clase 'EmbarquesResponse', solo una lista de EmbarqueDto