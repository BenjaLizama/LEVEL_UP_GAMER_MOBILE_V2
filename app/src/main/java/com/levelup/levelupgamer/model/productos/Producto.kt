package com.levelup.levelupgamer.model.productos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(

    @SerialName("codigoProducto")
    val codigo: String,

    @SerialName("nombreProducto")
    val nombre: String,

    @SerialName("descripcionProducto")
    val descripcion: String,

    @SerialName("precioProducto")
    val precio: Double,

    // ¡OJO AQUÍ! En tu JSON esto es una lista [...] (corchetes),
    // por eso usamos List<String> y no String solo.
    @SerialName("imagenesUrl")
    val imagenes: List<String>,

    @SerialName("cantidadStockProducto")
    val stock: Int
)
