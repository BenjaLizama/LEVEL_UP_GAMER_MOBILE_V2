package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.db.dao.ProductoDAO
import com.levelup.levelupgamer.db.entidades.Producto
import javax.inject.Inject


class ProductoRepository @Inject constructor(private val productoDao: ProductoDAO) {

    suspend fun obtenerProductos(categoria: String): List<Producto> {
        return productoDao.obtenerProductos(categoria)
    }

    suspend fun insertarProducto(producto: Producto) {
        productoDao.insertarProducto(producto)

    }
}

