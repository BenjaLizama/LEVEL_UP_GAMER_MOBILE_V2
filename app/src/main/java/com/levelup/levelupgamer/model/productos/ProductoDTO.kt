package com.levelup.levelupgamer.model.productos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable

    data class ProductoDto(
    @SerialName("codigoProducto") val id: String,
    @SerialName("nombreProducto") val nombre: String,
    @SerialName("precioProducto") val precio: Double,
    @SerialName("imagenesUrl") val imagenes: List<String>,
    @SerialName("descripcionProducto") val descripcion: String
    )

