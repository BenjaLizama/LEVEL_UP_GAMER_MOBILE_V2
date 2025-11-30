package com.levelup.levelupgamer.model.carrito

import kotlinx.serialization.Serializable

@Serializable
data class AgregarProdRequest (
    val codigoProducto: String,
    val cantidad: Int
)



