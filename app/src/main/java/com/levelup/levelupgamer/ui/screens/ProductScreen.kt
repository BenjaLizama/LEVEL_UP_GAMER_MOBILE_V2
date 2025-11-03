package com.levelup.levelupgamer.ui.screens

import androidx.compose.foundation.layout.Box
import com.levelup.levelupgamer.ui.components.organisms.ProcductoCard
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.levelup.levelupgamer.viewmodel.carrito.CarritoViewModel

import com.levelup.levelupgamer.viewmodel.productos.ProductListViewModel

@Composable
fun ProductScreen(listViewModel: ProductListViewModel = hiltViewModel(),carritoViewModel: CarritoViewModel = hiltViewModel()) {
    val uiState by listViewModel.uiState.collectAsState()

    when {

        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }


        uiState.error != null -> {
            Text(text = "Error: ${uiState.error}")
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = uiState.productos, key = { it.id }) { producto ->
                    ProcductoCard(
                        producto = producto,
                        onClick = {
                            listViewModel.onProductoClicked(producto.id)
                        }, onAddToCart = { carritoViewModel.agregarAlCarrito(producto.id) }


                    )
                }
            }
        }
    }
}

