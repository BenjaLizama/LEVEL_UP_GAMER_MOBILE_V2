package com.levelup.levelupgamer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.utils.formatPriceToCLP
import com.levelup.levelupgamer.viewmodel.carrito.CarritoEvent
import com.levelup.levelupgamer.viewmodel.carrito.CarritoItemUi // <--- Usamos el modelo nuevo
import com.levelup.levelupgamer.viewmodel.carrito.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CarritoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // 1. MANEJO DE EVENTOS (Actualizado para CarritoEvent)
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CarritoEvent.ProductoAgregadoExitosamente -> {
                    // Opcional: Mostrar mensaje o no hacer nada
                }
                is CarritoEvent.PagoExitoso -> {
                    Toast.makeText(context, "🎉 ¡Compra realizada!", Toast.LENGTH_LONG).show()
                    // Aquí podrías navegar: navController.navigate("home")
                }
                is CarritoEvent.Error -> {
                    snackbarHostState.showSnackbar(event.mensaje)
                }
            }
        }
    }
    LaunchedEffect(Unit) {viewModel.refrescarCarrito()}

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorFondo,
                    titleContentColor = Color.White
                ),
                title = { Text("Carrito") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorFondo)
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                !uiState.isLoading && uiState.products.isEmpty() -> {
                    Text(
                        text = "Tu carrito está vacío",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    CartContent(
                        items = uiState.products, // Lista de CarritoItemUi
                        total = uiState.total,    // El ViewModel ya calculó el total

                        // NOTA: Los IDs ahora son STRING
                        onIncrease = { codigo -> viewModel.agregarAlCarrito(codigo) },
                        onDecrease = { codigo ->
                            // TODO: Implementar lógica de disminuir cuando tengas la API
                            // viewModel.disminuirCantidad(codigo)
                        },
                        onRemove = { codigo -> viewModel.eliminarDelCarrito(codigo) },
                        onCheckoutClicked = { viewModel.onProcederAlPago() }
                    )
                }
            }
        }
    }
}

@Composable
fun CartContent(
    items: List<CarritoItemUi>,
    total: Double,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onRemove: (String) -> Unit,
    onCheckoutClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Key es codigoProducto
            items(items = items, key = { it.codigoProducto }) { item ->
                CartItemCard(
                    item = item,
                    onIncrease = { onIncrease(item.codigoProducto) },
                    onDecrease = { onDecrease(item.codigoProducto) },
                    onRemove = { onRemove(item.codigoProducto) }
                )
            }
        }

        // ZONA DE TOTAL Y PAGO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White.copy(alpha = 0.05f), shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val totalClp = formatPriceToCLP(total)
            Text(
                text = "Total: $totalClp",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onCheckoutClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceder al Pago")
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CarritoItemUi, // <--- Tipo actualizado
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // IMAGEN (Ahora viene directa del objeto fusionado)
            AsyncImage(
                model = item.imagenUrl,
                contentDescription = item.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                // DATOS DIRECTOS (Ya no usamos item.producto.nombre)
                Text(item.nombre, style = MaterialTheme.typography.titleMedium, maxLines = 1)

                val precioUnitarioClp = formatPriceToCLP(item.precio)
                Text(
                    "$precioUnitarioClp c/u",
                    style = MaterialTheme.typography.bodyMedium
                )

                val subtotalClp = formatPriceToCLP(item.subtotal)
                Text(
                    "Sub: $subtotalClp",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // BOTONES DE CANTIDAD
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.Remove, "Disminuir")
                }

                Text(
                    text = "${item.cantidad}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, "Aumentar")
                }
            }

            // ELIMINAR
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}