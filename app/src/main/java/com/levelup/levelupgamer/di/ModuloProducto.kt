package com.levelup.levelupgamer.di

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.levelup.levelupgamer.db.dao.ProductoDAO
import com.levelup.levelupgamer.db.entidades.Producto
import javax.inject.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModuloProducto(private val productoDaoProvider: Provider<ProductoDAO>) :
    RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val productoDao = productoDaoProvider.get()

                productoDao.insertarProducto(Producto(
                    id = 1,
                    nombre = "Control PS5",
                    precio = 90.00,
                    imagenURL = "https://cdn.awsli.com.br/684/684977/produto/290262631/poli-m424h115z2.jpg",
                    descripcion = "ta weno el plai",
                    categoria = "consolas"
                ))
                productoDao.insertarProducto(Producto(
                    id = 2,
                    nombre = "Teclado Gamer",
                    precio = 120.00,
                    imagenURL = "https://preview.redd.it/an-old-keyboard-from-work-v0-4heixs24c54e1.jpeg?auto=webp&s=d34fd11a7aa2208e98f5df98b724dd8ef23aa4fc",
                    descripcion = "ta weno el teclao",
                    categoria = "periferico"
                ))
                productoDao.insertarProducto(Producto(
                    id = 3,
                    nombre = "Mouse RGB",
                    precio = 45.00,
                    imagenURL = "https://previews.123rf.com/images/goce/goce1502/goce150200085/36658090-old-computer-mouse-on-white.jpg",
                    descripcion = "ta weno el mause de rgb",
                    categoria = "periferico"
                ))
            }
        }
    }