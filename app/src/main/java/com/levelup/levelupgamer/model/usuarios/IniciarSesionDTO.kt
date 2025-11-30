package com.levelup.levelupgamer.model.usuarios

import kotlinx.serialization.Serializable

@Serializable
data class IniciarSesionDTO(
    val correo: String,
    val contrasena: String
)
