package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.model.carrito.AgregarProdRequest
import com.levelup.levelupgamer.model.carrito.CarritoDTO
import com.levelup.levelupgamer.model.carrito.CarritoItemDTO
import com.levelup.levelupgamer.remote.CarritoServiceApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CarritoRepositoryTest {


    private val apiFalsa: CarritoServiceApi = mockk()


    private val repository = CarritoRepository(apiFalsa)

    @Test
    fun obtenerCarrito() = runTest {
        val idUsuario = 1L
        val carritoEsperado = CarritoDTO(
            idUsuario = 1,
            idCarrito = 1,
            items = listOf(CarritoItemDTO("JM-001", 1, 100.0, 100.0)),
            total = 100.0
        )


        coEvery { apiFalsa.obtenerCarrito(idUsuario) } returns carritoEsperado


        val resultado = repository.obtenerCarrito(idUsuario)


        assertEquals(carritoEsperado, resultado)
    }

    @Test
    fun agregarItemAlCarrito() = runTest {

        val idUsuario = 1L
        val codigoProducto = "JM-001"

        val carritoRespuesta = CarritoDTO(1, 1,100.0, items = listOf(CarritoItemDTO(codigoProducto, 1, 100.0, 100.0)))

        coEvery { apiFalsa.agregarItem(idUsuario, any()) } returns carritoRespuesta

        val exito = repository.agregarItemAlCarrito(idUsuario, codigoProducto)

        assertTrue(exito)
    }
}