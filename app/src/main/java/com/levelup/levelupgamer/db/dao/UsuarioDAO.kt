package com.levelup.levelupgamer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.levelup.levelupgamer.db.entidades.Usuario

@Dao
interface UsuarioDAO {

    @Query("SELECT * FROM tabla_usuario")
    fun obtenerTodosLosUsuarios(): MutableList<Usuario>

    @Insert
    fun agregarUsuario(usuario: Usuario): Long

}