package com.levelup.levelupgamer.db.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_usuario")
data class Usuario(
    @PrimaryKey
    val idUsuario: Long,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String,
)
