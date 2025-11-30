package com.levelup.levelupgamer.model.carrito

import kotlinx.serialization.Serializable

@Serializable
data class CarritoDTO(
    val idUsuario: Long,
    val idCarrito: Long,
    val total: Double,
    val items:List<CarritoItemDTO>
)

