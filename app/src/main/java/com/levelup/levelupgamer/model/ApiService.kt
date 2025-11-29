package com.levelup.levelupgamer.model

import com.levelup.levelupgamer.model.usuarios.CrearUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioRetornoDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/api/usuarios/")
    suspend fun crearUsuario(@Body usuario: CrearUsuarioDTO): UsuarioRetornoDTO

}