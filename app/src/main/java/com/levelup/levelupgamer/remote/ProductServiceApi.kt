package com.levelup.levelupgamer.remote


import com.levelup.levelupgamer.model.productos.Producto
import com.levelup.levelupgamer.model.productos.ProductoDto
import retrofit2.Call
import retrofit2.http.GET

interface ProductServiceApi {
    @GET("/api/productos")
    suspend fun obtenerProductos(): List<ProductoDto>
}