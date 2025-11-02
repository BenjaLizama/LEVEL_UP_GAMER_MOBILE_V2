package com.levelup.levelupgamer.ui.components.organisms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.levelup.levelupgamer.db.entidades.Producto


@Composable
fun ProcductoCard(producto: Producto, onClick: () -> Unit,onAddToCart: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {

            AsyncImage(
                model = producto.imagenURL, contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop

            )
            Column(modifier = modifier.padding(15.dp)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = producto.precio.toString(), style = MaterialTheme.typography.bodyLarge)
                Button(
                    onClick = { onAddToCart() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir al carrito")
                }
            }
        }


    }
}