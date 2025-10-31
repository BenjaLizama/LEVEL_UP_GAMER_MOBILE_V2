package com.levelup.levelupgamer.db.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "TABLA_CARRITO",
    primaryKeys = ["idUsuario", "idProducto"]
)
data class Carrito(
    val idUsuario: Long,
    val idProducto: Long,
    val cantidad: Int = 1
)
