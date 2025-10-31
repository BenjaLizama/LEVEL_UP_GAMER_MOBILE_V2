package com.levelup.levelupgamer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.levelup.levelupgamer.db.dao.UsuarioDAO
import com.levelup.levelupgamer.db.entidades.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
}