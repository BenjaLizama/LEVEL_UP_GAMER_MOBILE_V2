package com.levelup.levelupgamer.ui.screens




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.levelup.levelupgamer.db.entidades.CarritoItemConDetalles

import com.levelup.levelupgamer.viewmodel.carrito.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen( navController: NavHostController,viewModel: CarritoViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Carrito") }) }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                uiState.error != null -> {
                    Text(
                        text = "Error: ${uiState.error}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                !uiState.isLoading && uiState.products.isEmpty() -> {
                    Text(
                        text = "No hay productos en el carrito",
                        modifier = Modifier.align(Alignment.Center)
                    )

                }

                else -> {
                    CartContent(
                        items = uiState.products,
                        onIncrease = { productoId -> viewModel.agregarAlCarrito(productoId) },
                        onDecrease = { productoId ->
                            // ¡NECESITAREMOS AÑADIR ESTA FUNCIÓN!
                            viewModel.disminuirCantidad(productoId)
                        },
                        onRemove = { productoId ->
                            // ¡NECESITAREMOS AÑADIR ESTA FUNCIÓN!
                            viewModel.eliminarDelCarrito(productoId)
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun CartContent(
    items: List<CarritoItemConDetalles>,
    onIncrease: (Long) -> Unit,
    onDecrease: (Long) -> Unit,
    onRemove: (Long) -> Unit
) {
    val total = items.sumOf { it.producto.precio * it.cantidad }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = items, key = { it.producto.id }) { item ->
                CartItemCard(
                    item = item,
                    onIncrease = { onIncrease(item.producto.id) },
                    onDecrease = { onDecrease(item.producto.id) },
                    onRemove = { onRemove(item.producto.id) }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total: $${String.format("%.2f", total)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: Ir a la pantalla de pago */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceder al Pago")
            }
        }

    }

}

@Composable
fun CartItemCard(
    item: CarritoItemConDetalles,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(modifier= Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = item.producto.imagenURL,
                contentDescription = item.producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    "$${item.producto.precio}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.Remove, "Disminuir")
                }
                Text(text = "${item.cantidad}", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, "Aumentar")
                }
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
