package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.db.dao.CarritoDAO
import com.levelup.levelupgamer.db.entidades.Carrito
import com.levelup.levelupgamer.db.entidades.CarritoItemConDetalles
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CarritoRepository @Inject constructor(private val carritoDAO: CarritoDAO) {

    suspend fun agregarProductoAlCarrito(idProducto: Long, idUsuario: Long) {
        val itemExiste = carritoDAO.obtenerItem( idUsuario,idProducto)

        if (itemExiste == null) {
            val nuevoItem = Carrito(
                idUsuario = idUsuario,
                idProducto = idProducto,
                cantidad = 1
            )
            carritoDAO.agregarAlCarrito(nuevoItem)

        } else {
            carritoDAO.aumentarCantidad(idProducto, idUsuario)
        }
    }

    fun getItemsDelCarritoConDetalles(idusuario: Long): Flow<List<CarritoItemConDetalles>> {
        return carritoDAO.getItemsDelCarritoConDetalles(idusuario)
    }
    suspend fun disminuirProductoDelCarrito(idProducto: Long, idUsuario: Long) {
        // Revisa el item
        val itemExistente = carritoDAO.obtenerItem(idUsuario, idProducto)
        if (itemExistente != null) {
            if (itemExistente.cantidad > 1) {
                // Si hay más de 1, solo resta
                carritoDAO.disminuirCantidad(idProducto, idUsuario)
            } else {
                // Si solo queda 1, elimina la fila
                carritoDAO.eliminarItem(idProducto, idUsuario)
            }
        }
    }

    suspend fun eliminarProductoDelCarrito(idProducto: Long, idUsuario: Long) {
        carritoDAO.eliminarItem(idProducto, idUsuario)
    }

}