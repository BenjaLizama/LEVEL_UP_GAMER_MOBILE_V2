package com.levelup.levelupgamer.viewmodel.autenticacion

data class EstadoFormularioUI(
    // Entradas
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",

    val correoInicio: String = "",
    val contrasenaInicio: String = "",

    // Errores
    val nombreError: String? = null,
    val apellidoError: String? = null,
    val correoError: String? = null,
    val contrasenaError: String? = null,
    val confirmarContrasenaError: String? = null,

    val correoErrorInicio: String? = null,
    val contrasenaErrorInicio: String? = null,

    // Estados globales
    val isLoading: Boolean = false,
    val creacionExitosa: Boolean = false,
    val inicioExitoso: Boolean = false,
    val mensajeError: String? = null
)
