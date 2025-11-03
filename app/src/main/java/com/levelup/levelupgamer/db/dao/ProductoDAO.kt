package com.levelup.levelupgamer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.levelup.levelupgamer.db.entidades.Producto

@Dao
interface ProductoDAO {

    @Query("SELECT * FROM TABLA_PRODUCTO WHERE CATEGORIA LIKE '%' || :categoria || '%'")
    suspend fun obtenerProductos(categoria: String): List<Producto>

    @Insert
    suspend fun insertarProducto(producto: Producto)
}