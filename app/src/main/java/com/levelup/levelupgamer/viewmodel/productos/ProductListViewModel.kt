package com.levelup.levelupgamer.viewmodel.productos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.db.entidades.Producto
import com.levelup.levelupgamer.db.repository.ProductoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListViewModel @Inject constructor(private val productoRepository: ProductoRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(ProductListUiState(isLoading = true))
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()
    val categoria = ""

    init {

        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            val mockProductos = productoRepository.obtenerProductos(categoria)

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