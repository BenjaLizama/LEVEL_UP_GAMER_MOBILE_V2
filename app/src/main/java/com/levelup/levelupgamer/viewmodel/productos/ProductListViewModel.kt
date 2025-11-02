package com.levelup.levelupgamer.viewmodel.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.db.entidades.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(ProductListUiState(isLoading = true))
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()


    init {

        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            val mockProductos = listOf(
                Producto(
                    1,
                    "Control PS5",
                    "$90.00",
                    "https://i.blogs.es/15cd4e/captura_de_pantalla_2024-09-10_a_las_17.08.51/650_1200.jpeg",
                    1.0
                ),
                Producto(
                    2,
                    "Teclado Gamer",
                    "$120.00",
                    "https://preview.redd.it/an-old-keyboard-from-work-v0-4heixs24c54e1.jpeg?auto=webp&s=d34fd11a7aa2208e98f5df98b724dd8ef23aa4fc",
                    1.0
                ),
                Producto(
                    3,
                    "Mouse RGB",
                    "$45.00",
                    "https://m.media-amazon.com/images/I/61Mk3YqYHpL._AC_SL1500_.jpg",
                    233.0
                )
            )

            _uiState.update { estadoActual ->
                estadoActual.copy(
                    productos = mockProductos,
                    isLoading = false
                )
            }


        }

    }

    fun onProductoClicked(productoId: Long) {

        println("Clic en el producto con ID: $productoId")

    }
}

data class ProductListUiState(
    val isLoading: Boolean = false,
    val productos: List<Producto> = emptyList(),
    val error: String? = null
)