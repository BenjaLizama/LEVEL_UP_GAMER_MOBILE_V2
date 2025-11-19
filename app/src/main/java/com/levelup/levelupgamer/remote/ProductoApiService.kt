package com.levelup.levelupgamer.remote

import com.levelup.levelupgamer.model.ProductoRetornoDTO
import retrofit2.http.GET

interface ProductoApiService {
    @GET("/")
    suspend fun getProductos(): List<ProductoRetornoDTO>
}