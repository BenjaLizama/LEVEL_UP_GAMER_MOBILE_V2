package com.levelup.levelupgamer.model.usuarios

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioLogeadoDTO(
    val idUsuario: Long,
    val nombreUsuario: String,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val imagenPerfilURL: String?,
    val token: String?
)
