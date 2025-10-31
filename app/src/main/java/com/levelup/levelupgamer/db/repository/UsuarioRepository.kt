package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.db.dao.UsuarioDAO
import com.levelup.levelupgamer.db.entidades.Usuario
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDAO: UsuarioDAO
) {
    suspend fun crearCuenta(usuario: Usuario): Long {
        return usuarioDAO.agregarUsuario(usuario)
    }

    suspend fun buscarUsuarioPorCorreo(correo: String): Boolean {
        return usuarioDAO.obtenerUsuarioPorCorreo(correo) != null
    }
}