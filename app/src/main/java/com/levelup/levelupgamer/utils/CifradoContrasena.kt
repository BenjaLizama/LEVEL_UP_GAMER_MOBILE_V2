package com.levelup.levelupgamer.utils

import org.mindrot.jbcrypt.BCrypt

object CifradoContrasena {
    fun cifrarContrasena(contrasena: String): String {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt(12))
    }

    fun verificarContrasena(contrasena: String, contrasenaCifrada: String): Boolean {



        return try {
            BCrypt.checkpw(contrasena, contrasenaCifrada)
        } catch (e: Exception) {
            false
        }
    }
}