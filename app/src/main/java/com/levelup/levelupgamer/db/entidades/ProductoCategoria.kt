package com.levelup.levelupgamer.db.entidades

import androidx.room.Entity

@Entity(
    tableName = "TABLA_PRODUCTO_CATEGORIA",
    primaryKeys = ["productoId", "categoriaId"]
)
data class ProductoCategoria(
    val productoId: Long,
    val categoriaId: Long
)
