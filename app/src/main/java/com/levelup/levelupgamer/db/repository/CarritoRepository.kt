package com.levelup.levelupgamer.db.repository


import com.levelup.levelupgamer.model.carrito.AgregarProdRequest
import com.levelup.levelupgamer.model.carrito.CarritoDTO
import com.levelup.levelupgamer.remote.CarritoServiceApi

import javax.inject.Inject


class CarritoRepository @Inject constructor(private val api: CarritoServiceApi) {
    suspend fun obtenerCarrito(idUsuario: Long): CarritoDTO? {
        return try {
            println("DEBUG:intentamos obtener el carrito de usuario $idUsuario")
            val carrito = api.obtenerCarrito(idUsuario)
            println("DEBUG:carrito obtenido $carrito")
            carrito
        } catch (e: Exception) {
            println("DEBUG:error al obtener el carrito ${e.message}")
            e.printStackTrace()
            null
        }
    }

    suspend fun agregarItemAlCarrito(idUsuario: Long, codigoProducto: String): Boolean {
        return try {
            val request = AgregarProdRequest(
                codigoProducto = codigoProducto,
                cantidad = 1
            )
            val carritoActualizado = api.agregarItem(idUsuario, request)

            println("DEBUG_REPO: Agregado con éxito. Items actuales: ${carritoActualizado.items.size}")
            true
        }catch (e: Exception) {
            println("DEBUG_REPO: Error al agregar: ${e.message}")
            e.printStackTrace()
            false}
    }
}

