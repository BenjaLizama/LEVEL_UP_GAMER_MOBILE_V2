package com.levelup.levelupgamer.remote

import com.levelup.levelupgamer.model.carrito.AgregarProdRequest
import com.levelup.levelupgamer.model.carrito.CarritoDTO
import com.levelup.levelupgamer.model.productos.ProductoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CarritoServiceApi {
    @GET("api/carritos/{id}")
    suspend fun obtenerCarrito(
        @Path("id") idUsuario: Long
    ): CarritoDTO

    @POST("api/carritos/{id}")
    suspend fun agregarItem(
        @Path("id") idUsuario: Long,
        @Body producto: AgregarProdRequest
    ): CarritoDTO
}