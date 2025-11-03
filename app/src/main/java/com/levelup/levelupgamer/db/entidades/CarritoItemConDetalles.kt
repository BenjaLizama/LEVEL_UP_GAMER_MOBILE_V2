package com.levelup.levelupgamer.db.entidades

import androidx.room.Embedded

data class CarritoItemConDetalles(
    // Todos los campos de tu entidad 'Producto'
    @Embedded
    val producto: Producto,

    // El campo extra de la tabla 'CarritoItem'
    val cantidad: Int
)