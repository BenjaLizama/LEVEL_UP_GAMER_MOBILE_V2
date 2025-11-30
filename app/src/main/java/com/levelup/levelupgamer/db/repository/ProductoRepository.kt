package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.model.productos.ProductoDto
import com.levelup.levelupgamer.remote.ProductServiceApi
import jakarta.inject.Inject

class ProductoRepository @Inject constructor(
    private val api: ProductServiceApi
) {

    suspend fun obtenerProductos(categoria: String): List<ProductoDto> {
        return try {

            api.obtenerProductos()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}