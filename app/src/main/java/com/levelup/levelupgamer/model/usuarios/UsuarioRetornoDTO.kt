package com.levelup.levelupgamer.model.usuarios

data class UsuarioRetornoDTO(
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val contrasena: String,
    val nombreUsuario: String,
)
