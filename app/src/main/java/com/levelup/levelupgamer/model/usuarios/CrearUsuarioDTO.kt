package com.levelup.levelupgamer.model.usuarios

import java.util.Date


data class CrearUsuarioDTO (
    val correo: String,
    val contrasena: String,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: Date,
    val nombreUsuario: String
)