package org.example.project.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class BuscarEmbarquePorIdRequest(
    val EmbarqueID: Int
)