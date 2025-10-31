package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.db.dao.UsuarioDAO
import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.utils.CifradoContrasena
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioDAO: UsuarioDAO
) {
    suspend fun crearCuenta(usuario: Usuario): Long {
        val contrasenaCifrada = CifradoContrasena.cifrarContrasena(usuario.contrasena)
        val nuevoUsuario = usuario.copy(contrasena = contrasenaCifrada)

        return usuarioDAO.agregarUsuario(nuevoUsuario)
    }

    suspend fun buscarUsuarioPorCorreo(correo: String): Boolean {
        return usuarioDAO.obtenerUsuarioPorCorreo(correo) != null
    }

    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario? {
        val usuario = usuarioDAO.obtenerUsuarioPorCorreo(correo)

        if (usuario == null) {
            return null
        }

        return usuario
    }

    suspend fun validarCredenciales(correo: String, contrasenaIngresada: String): Boolean {
        val usuario = usuarioDAO.obtenerUsuarioPorCorreo(correo)

        if (usuario == null) {
            return false
        }

        val contrasenaCifrada = usuario.contrasena
        val esValido = CifradoContrasena.verificarContrasena(contrasenaIngresada, contrasenaCifrada)

        return esValido
    }

}