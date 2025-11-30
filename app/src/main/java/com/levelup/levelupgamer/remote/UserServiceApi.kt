package com.levelup.levelupgamer.remote

import com.levelup.levelupgamer.model.usuarios.AgregarUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.IniciarSesionDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioLogeadoDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface UserServiceApi {

    @POST("api/usuarios")
    suspend fun agregarUsuario(
        @Body request: AgregarUsuarioDTO
    ): UsuarioResponseDTO?

    @POST("api/usuarios/login")
    suspend fun iniciarSesion(
        @Body request: IniciarSesionDTO
    ): UsuarioLogeadoDTO

}