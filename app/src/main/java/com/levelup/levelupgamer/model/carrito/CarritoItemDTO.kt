package com.levelup.levelupgamer.model.carrito

import kotlinx.serialization.Serializable

@Serializable
data class CarritoItemDTO(
    val codigoProducto: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double

)



