package com.levelup.levelupgamer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.levelup.levelupgamer.db.dao.UsuarioDAO
import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.db.entidades.Producto
import com.levelup.levelupgamer.db.entidades.Categoria
import com.levelup.levelupgamer.db.entidades.ProductoCategoria
import com.levelup.levelupgamer.db.entidades.Carrito

@Database(
    entities = [
       Usuario::class,
       Producto::class,
       Categoria::class,
       ProductoCategoria::class,
       Carrito::class
   ],
    version = 3
)
abstract class BaseDatos : RoomDatabase() {
    abstract fun getUsuarioDao(): UsuarioDAO
}