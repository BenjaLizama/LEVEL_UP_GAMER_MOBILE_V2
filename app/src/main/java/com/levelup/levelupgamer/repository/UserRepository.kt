package com.levelup.levelupgamer.repository

import com.levelup.levelupgamer.model.usuarios.AgregarUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.IniciarSesionDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioLogeadoDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioResponseDTO

interface UserRepository {

    suspend fun agregarUsuario(agregarUsuarioDTO: AgregarUsuarioDTO): UsuarioResponseDTO?

    suspend fun iniciarSesion(credenciales: IniciarSesionDTO): UsuarioLogeadoDTO?

}