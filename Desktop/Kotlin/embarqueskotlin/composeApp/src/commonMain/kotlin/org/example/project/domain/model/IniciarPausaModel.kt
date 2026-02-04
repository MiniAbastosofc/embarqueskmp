package org.example.project.domain.model

import kotlin.time.ExperimentalTime

data class IniciarPausaModel @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val registroId: Int,
    val tipoRegistro: String,  // "Embarque" o "Extension"
    val usuarioId: Int,
    val fechaInicio: String,   // Mantén como String para simplicidad
    val motivo: String,
    val tipo: String
)