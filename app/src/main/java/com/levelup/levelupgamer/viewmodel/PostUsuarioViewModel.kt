package com.levelup.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.model.usuarios.CrearUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioRetornoDTO
import com.levelup.levelupgamer.repository.usuarios.PostUsuarios
import kotlinx.coroutines.launch
import java.util.Date

class PostUsuarioViewModel: ViewModel() {

    private val repository = PostUsuarios()

    fun crearNuevoUsuario(
        correo: String,
        contrasena: String,
        nombre: String,
        apellido: String,
        fechaNacimiento: Date,
        nombreUsuario: String
    ) {
        val nuevoUsuarioDTO = CrearUsuarioDTO(
            correo = correo,
            contrasena = contrasena,
            nombre = nombre,
            apellido = apellido,
            fechaNacimiento = fechaNacimiento,
            nombreUsuario = nombreUsuario
        )

        viewModelScope.launch {
            try {
                val respuesta: UsuarioRetornoDTO = repository.crearUsuario(nuevoUsuarioDTO)
                println("Usuario creado con exito: ${respuesta.nombreUsuario}")
            } catch (e: Exception) {
                println("Error al crear el usuario: ${e.message}")
            }
        }
    }
}