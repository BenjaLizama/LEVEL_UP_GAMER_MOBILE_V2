package com.levelup.levelupgamer.db.repository

import com.levelup.levelupgamer.model.productos.ProductoDto
import com.levelup.levelupgamer.remote.ProductServiceApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class ProductoRepositoryTest {
    private val apiFalsa: ProductServiceApi = mockk()
    private val repository = ProductoRepository(apiFalsa)

    @Test
    fun obtenerProductos() = runTest {
        val productoDummy = ProductoDto(
            "JM-1", "monopoly", 2000.0, listOf("url1", "url2"), "juego de mesa para toda la familia"
        )
        val productosEsperados = listOf(productoDummy)

        coEvery { apiFalsa.obtenerProductos() } returns productosEsperados
        val resultado = repository.obtenerProductos("")
        assertEquals(productosEsperados, resultado)


    }

    @Test
    fun `obtenerProductos debe devolver lista VACIA si la API falla`() = runTest {

        coEvery { apiFalsa.obtenerProductos() } throws RuntimeException("Error 500")

        val resultado = repository.obtenerProductos("Juegos")

        assertTrue(resultado.isEmpty())
    }

    @Test
    fun buscarProducto() = runTest {
        val codigoProducto = "JM-12"
        val productoEsperado = ProductoDto(
            "JM-12",
            "monopoly",
            2000.0,
            listOf("url1", "url2"),
            "juego de mesa para toda la familia"
        )
        coEvery { apiFalsa.buscarProducto(codigoProducto) } returns productoEsperado
        val resultado = repository.buscarProducto(codigoProducto)
        assertEquals(productoEsperado, resultado)
    }

    }

