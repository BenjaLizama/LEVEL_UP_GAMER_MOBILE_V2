package com.levelup.levelupgamer.viewmodel.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import com.levelup.levelupgamer.db.repository.CarritoRepository
import com.levelup.levelupgamer.db.repository.ProductoRepository // <--- Necesario para las fotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CarritoEvent {
    object ProductoAgregadoExitosamente : CarritoEvent
    data class Error(val mensaje: String) : CarritoEvent
    object PagoExitoso : CarritoEvent
}

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val carritoRepository: CarritoRepository,
    private val productoRepository: ProductoRepository, // Inyectamos el catálogo
    private val preferenciasUsuarioRepository: PreferenciasUsuarioRepository
) : ViewModel() {

    // Estado UI
    private val _uiState = MutableStateFlow(CartUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()


    private val _events = MutableSharedFlow<CarritoEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()


    private var currentUserId: Long = 0L

    init {

        observarUsuario()
    }

    private fun observarUsuario() {
        viewModelScope.launch {
            preferenciasUsuarioRepository.idUsuario.collectLatest { idUsuario ->
                currentUserId = idUsuario
                if (idUsuario != 0L) {
                    cargarCarritoCompleto(idUsuario)
                } else {

                    _uiState.update { it.copy(isLoading = false, products = emptyList()) }
                }
            }
        }
    }

    fun cargarCarritoCompleto(idUsuario: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {

                val carritoDto = carritoRepository.obtenerCarrito(idUsuario)

                if (carritoDto != null && carritoDto.items.isNotEmpty()) {


                    val catalogoProductos = productoRepository.obtenerProductos("")


                    val listaCombinada = carritoDto.items.mapNotNull { itemCarrito ->


                        val detalleProducto = catalogoProductos.find {
                            it.id == itemCarrito.codigoProducto
                        }

                        if (detalleProducto != null) {
                            CarritoItemUi(
                                codigoProducto = itemCarrito.codigoProducto,
                                nombre = detalleProducto.nombre,
                                imagenUrl = detalleProducto.imagenes?.firstOrNull() ?: "",
                                precio = itemCarrito.precioUnitario,
                                cantidad = itemCarrito.cantidad,
                                subtotal = itemCarrito.subtotal
                            )
                        } else {

                            null
                        }
                    }


                    val totalCompra = listaCombinada.sumOf { it.subtotal }

                    _uiState.update {
                        it.copy(isLoading = false, products = listaCombinada, total = totalCompra)
                    }

                } else {
                    _uiState.update { it.copy(isLoading = false, products = emptyList()) }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
    fun refrescarCarrito(){
        if(currentUserId!=0L){
            println("DEBUG:refrescando carrito")
            cargarCarritoCompleto(currentUserId)
        }
    }


    fun agregarAlCarrito(codigoProducto: String) {
        viewModelScope.launch {

            if (currentUserId != 0L) {


                val exito = carritoRepository.agregarItemAlCarrito(currentUserId, codigoProducto)

                if (exito) {

                    _events.emit(CarritoEvent.ProductoAgregadoExitosamente)


                    cargarCarritoCompleto(currentUserId)
                } else {
                    _events.emit(CarritoEvent.Error("No se pudo agregar el producto"))
                }
            } else {
                _events.emit(CarritoEvent.Error("Debes iniciar sesión"))
            }
        }
    }

    fun eliminarDelCarrito(codigoProducto: String) {
        viewModelScope.launch {

            cargarCarritoCompleto(currentUserId)
        }
    }

    fun onProcederAlPago() {
        viewModelScope.launch {
            // Lógica de pago
            _events.emit(CarritoEvent.PagoExitoso)
        }
    }
}

data class CarritoItemUi(
    val codigoProducto: String,
    val nombre: String,
    val imagenUrl: String,
    val precio: Double,
    val cantidad: Int,
    val subtotal: Double
)
data class CartUiState(
    val isLoading: Boolean = false,
    val products: List<CarritoItemUi> = emptyList(), // <--- Usamos la clase híbrida
    val total: Double = 0.0,
    val error: String? = null
)