package com.levelup.levelupgamer.viewmodel.productos



import com.levelup.levelupgamer.db.repository.ProductoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
// 1. CAMBIO IMPORTANTE: Importamos el DTO, no la entidad de BD
import com.levelup.levelupgamer.model.productos.ProductoDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject // O jakarta.inject, depende de tu versión de Hilt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productoRepository: ProductoRepository
) : ViewModel() {

    // Inicializamos el estado
    private val _uiState = MutableStateFlow(ProductListUiState(isLoading = true))
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()
    val categoria = ""

    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            // El repo ahora devuelve List<ProductoDto>, así que lo guardamos tal cual
            val productosReales = productoRepository.obtenerProductos(categoria)

            _uiState.update { estadoActual ->
                estadoActual.copy(
                    productos = productosReales, // Coincide con el tipo del UiState
                    isLoading = false
                )
            }
        }
    }

    // 2. CAMBIO IMPORTANTE: El ID en tu JSON es String ("JM-001"), no Long
    fun onProductoClicked(productoId: String) {
        println("Clic en el producto con ID: $productoId")
    }
}

// 3. CAMBIO IMPORTANTE: El estado ahora guarda una lista de DTOs
data class ProductListUiState(
    val isLoading: Boolean = false,
    val productos: List<ProductoDto> = emptyList(), // <--- AQUÍ estaba el error principal
    val error: String? = null
)