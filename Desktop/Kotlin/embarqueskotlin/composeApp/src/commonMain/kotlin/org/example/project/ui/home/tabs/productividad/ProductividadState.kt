package org.example.project.ui.home.tabs.productividad

import org.example.project.domain.model.ProductividadModel

data class ProductividadState(
    val estibadores: List<ProductividadModel> = emptyList(),
    val checadores: List<ProductividadModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val fechaInicio: String = "", // Para mostrar en el DatePicker
    val fechaFin: String = ""
)