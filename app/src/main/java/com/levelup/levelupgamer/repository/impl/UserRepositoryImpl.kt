package com.levelup.levelupgamer.repository.impl

import com.levelup.levelupgamer.model.usuarios.AgregarUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.IniciarSesionDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioLogeadoDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioResponseDTO
import com.levelup.levelupgamer.remote.UserServiceApi
import com.levelup.levelupgamer.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userServiceApi: UserServiceApi
): UserRepository{
    override suspend fun agregarUsuario(agregarUsuarioDTO: AgregarUsuarioDTO): UsuarioResponseDTO? {
        return try {
            userServiceApi.agregarUsuario(agregarUsuarioDTO)
        } catch (e: Exception) {
            println("Error al agregar usuario: $e")
            null
        }
    }

    override suspend fun iniciarSesion(credenciales: IniciarSesionDTO): UsuarioLogeadoDTO? {
        return try {
            userServiceApi.iniciarSesion(credenciales)
        } catch (e: Exception) {
            println("Error al iniciar sesion: $e")
            null
        }
    }
}