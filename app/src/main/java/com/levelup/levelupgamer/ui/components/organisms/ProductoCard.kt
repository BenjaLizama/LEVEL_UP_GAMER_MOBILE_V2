package com.levelup.levelupgamer.ui.components.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
// import com.levelup.levelupgamer.db.entidades.Producto // <--- YA NO NECESITAS ESTO
import com.levelup.levelupgamer.model.productos.ProductoDto
import com.levelup.levelupgamer.ui.theme.ColorAcento
import com.levelup.levelupgamer.ui.theme.ColorTextoPrincipal
import com.levelup.levelupgamer.utils.formatPriceToCLP

@Composable
fun ProductoCard(
    producto: ProductoDto, // ✅ Correcto, recibimos el DTO
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val CardBackgroundColor = Color(0xFF1F1F1F)

    // LÓGICA DE IMAGEN: El DTO tiene una lista, sacamos la primera o dejamos vacío
    val imagenParaMostrar = producto.imagenes?.firstOrNull() ?: ""

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp)
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
            ) {
                AsyncImage(
                    model = imagenParaMostrar, // ✅ Usamos la variable que calculamos arriba
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre, // ✅ CAMBIO: nombre -> nombreProducto
                        color = ColorTextoPrincipal,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // NOTA: Tu JSON de ejemplo NO tenía categoría.
                    // Lo cambié a una descripción corta o puedes borrar este Text si prefieres.
                    Text(
                        text = producto.descripcion, // ✅ CAMBIO: Categoria -> descripcionProducto
                        color = Color.LightGray.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // ✅ CAMBIO: precio -> precioProducto
                    val precioClp = formatPriceToCLP(producto.precio)
                    Text(
                        text = precioClp,
                        color = ColorAcento,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Button(
                    onClick = { onAddToCart() },
                    modifier = Modifier
                        .width(150.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorAcento.copy(alpha = 0.8f)
                    ),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(
                        text = "Añadir al carrito",
                        color = ColorTextoPrincipal,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

