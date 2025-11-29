package com.levelup.levelupgamer.repository.usuarios

import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.model.RetrofitInstance
import com.levelup.levelupgamer.model.usuarios.CrearUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioRetornoDTO

class PostUsuarios {

    suspend fun crearUsuario(usuario: CrearUsuarioDTO): UsuarioRetornoDTO {
        return RetrofitInstance.api.crearUsuario(usuario)
    }
}