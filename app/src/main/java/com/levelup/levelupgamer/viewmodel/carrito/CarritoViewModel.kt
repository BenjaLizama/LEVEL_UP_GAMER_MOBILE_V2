package com.levelup.levelupgamer.viewmodel.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import com.levelup.levelupgamer.db.entidades.CarritoItemConDetalles
import com.levelup.levelupgamer.db.repository.CarritoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartUiState(
    val isLoading: Boolean = false,
    val products: List<CarritoItemConDetalles> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritoRepository: CarritoRepository,
    private val preferenciasUsuarioRepository: PreferenciasUsuarioRepository
) : ViewModel() {
    private val idUsuarioFlow: Flow<Long> = preferenciasUsuarioRepository.idUsuario

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<CartUiState> = idUsuarioFlow.flatMapLatest { idUsuario ->

        // --- 1. LÓGICA CORREGIDA ---
        // Si el ID es 0 (no logueado), devuelve un estado vacío.
        if (idUsuario == 0L) {
            flowOf(CartUiState(isLoading = false, products = emptyList()))
        } else {
            // Si hay un ID, ¡empieza a observar el carrito de ESE usuario!
            carritoRepository.getItemsDelCarritoConDetalles(idUsuario)
                .map { listaItems ->
                    // Convierte la lista de la BD al estado de la UI
                    CartUiState(isLoading = false, products = listaItems)
                }
                .catch { e ->
                    // Maneja errores de la base de datos
                    emit(CartUiState(isLoading = false, error = e.message))
                }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartUiState(isLoading = true))

    fun agregarAlCarrito(idProducto: Long) {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            println("Añadiendo producto. ID de Usuario es: $idUsuario, ID de Producto es: $idProducto")
            // --- 2. COMPROBACIÓN DE SEGURIDAD CORREGIDA ---
            // ¡Solo llama al repositorio si el usuario está logueado!
            if (idUsuario != 0L) {
                carritoRepository.agregarProductoAlCarrito(idProducto, idUsuario)
            }
        }
    }

    // (Añade también la función de eliminar, con la misma comprobación de seguridad)

    fun disminuirCantidad(idProducto: Long) {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            if (idUsuario != 0L) {
                // (Llamaremos a esta nueva función en el Repositorio)
                carritoRepository.disminuirProductoDelCarrito(idProducto, idUsuario)
            }
        }
    }

    fun eliminarDelCarrito(idProducto: Long) {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            if (idUsuario != 0L) {
                // (Llamaremos a esta nueva función en el Repositorio)
                carritoRepository.eliminarProductoDelCarrito(idProducto, idUsuario)
            }
        }
    }
}