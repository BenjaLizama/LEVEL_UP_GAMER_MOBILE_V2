package com.levelup.levelupgamer.viewmodel.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import com.levelup.levelupgamer.db.entidades.CarritoItemConDetalles
import com.levelup.levelupgamer.db.repository.CarritoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CartUiState(
    val isLoading: Boolean = false,
    val products: List<CarritoItemConDetalles> = emptyList(),
    val error: String? = null
)

sealed interface CarritoEvent {
    object ProductoAgregadoExitosamente : CarritoEvent
}

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritoRepository: CarritoRepository,
    private val preferenciasUsuarioRepository: PreferenciasUsuarioRepository
) : ViewModel() {
    private val idUsuarioFlow: Flow<Long> = preferenciasUsuarioRepository.idUsuario

    private val _eventFlow = MutableSharedFlow<String>()

    val eventFlow = _eventFlow.asSharedFlow()
    private val _events = MutableSharedFlow<CarritoEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<CarritoEvent> = _events.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<CartUiState> = idUsuarioFlow.flatMapLatest { idUsuario ->

        if (idUsuario == 0L) {
            flowOf(CartUiState(isLoading = false, products = emptyList()))
        } else {
            carritoRepository.getItemsDelCarritoConDetalles(idUsuario)
                .map { listaItems ->
                    CartUiState(isLoading = false, products = listaItems)
                }
                .catch { e ->
                    emit(CartUiState(isLoading = false, error = e.message))
                }
        }

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CartUiState(isLoading = true)
    )

    fun agregarAlCarrito(idProducto: Long) {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            println("Añadiendo producto. ID de Usuario es: $idUsuario, ID de Producto es: $idProducto")

            if (idUsuario != 0L) {
                carritoRepository.agregarProductoAlCarrito(idProducto, idUsuario)
                _events.emit(CarritoEvent.ProductoAgregadoExitosamente)
            }
        }
    }

    fun disminuirCantidad(idProducto: Long) {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            if (idUsuario != 0L) {
                carritoRepository.disminuirProductoDelCarrito(idProducto, idUsuario)
            }
        }
    }

    fun eliminarDelCarrito(idProducto: Long) {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            if (idUsuario != 0L) {
                carritoRepository.eliminarProductoDelCarrito(idProducto, idUsuario)
            }
        }
    }

    fun onProcederAlPago() {
        viewModelScope.launch {
            val idUsuario = idUsuarioFlow.first()
            if (idUsuario != 0L) {
                carritoRepository.vaciarCarritoDelUsuario(idUsuario)
                _eventFlow.emit("¡Pago realizado con éxito!")


            }
        }
    }
}