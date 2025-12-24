package org.example.project.domain

import org.example.project.domain.model.DetalleEmbarqueCompleto
import org.example.project.domain.model.RutaDetallesModel

class GetShipmentDetailsByDateUseCase(private val repository: Repository) {
    // Esto es correcto, devuelve una lista simple
    suspend operator fun invoke(date: String): List<RutaDetallesModel> {
        return repository.getShipmentDetailsByDateRepository(date)
    }
}

class GetShipmentDetailsByIdUseCase(private val repository: Repository) {
    suspend operator fun invoke(shipmentId: Int): DetalleEmbarqueCompleto {
        return repository.getShipmentDetailsByIdRepository(shipmentId)
    }
}