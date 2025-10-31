package com.levelup.levelupgamer.db.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TABLA_CATEGORIA")
data class Categoria (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nombre: String
)