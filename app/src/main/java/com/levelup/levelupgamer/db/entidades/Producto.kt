package com.levelup.levelupgamer.db.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TABLA_PRODUCTO")
data class Producto (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val imagenURL: String,
    val precio: Double,
    val categoria: String,
)