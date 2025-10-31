package com.levelup.levelupgamer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.levelup.levelupgamer.db.entidades.Usuario

@Dao
interface UsuarioDAO {

    @Query("SELECT * FROM tabla_usuario")
    suspend fun obtenerTodosLosUsuarios(): MutableList<Usuario>

    @Insert
    suspend fun agregarUsuario(usuario: Usuario): Long

    @Query("SELECT * FROM tabla_usuario WHERE correo = :correo")
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario?

    @Query("SELECT * FROM tabla_usuario WHERE idUsuario = :idUsuario")
    suspend fun obtenerUsuarioPorId(idUsuario: Long): Usuario?

    @Query("SELECT * FROM TABLA_USUARIO WHERE correo = :correo AND contrasena = :contrasena")
    suspend fun obtenerUsuarioPorCredenciales(correo: String, contrasena: String): Usuario?

}