package org.example.project.domain.model

//data class RutasModel(
//    val id: Int,
//    val fecha: String,
//    val ruta: String,
//    val rutaDescripcion: String,
//    val camion: String,
//    val camionDescripcion: String,
//    val cantidadPedidos: Int,
//    val statusId: Int,
//    val avance: Int
//)
data class RutasModel(
    val id: Int,
    val tipo: String,
    val titulo: String,
    val subtitulo: String,
    val detalle: String,
    val estado: Int,
    val porcentaje: Int,
    val ruta: String?,
    val camion: String?,
    val origen: String?,
    val destino: String?,
    val cantidad: Int
)