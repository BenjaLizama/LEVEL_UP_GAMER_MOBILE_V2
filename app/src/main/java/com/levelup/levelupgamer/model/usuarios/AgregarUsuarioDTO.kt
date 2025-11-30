package com.levelup.levelupgamer.model.usuarios

import kotlinx.serialization.Serializable

@Serializable
data class AgregarUsuarioDTO(
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val nombreUsuario: String
)
