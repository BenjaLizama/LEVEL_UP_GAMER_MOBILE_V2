package com.levelup.levelupgamer.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levelup.levelupgamer.ui.theme.ColorTextoSecundario

@Composable
fun ContainerProfileOptions(opciones: List<Pair<ImageVector, String>>, texto: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Text(
            text = texto,
            color = ColorTextoSecundario,
            fontSize = 20.sp
        )
        opciones.forEach { (icono, texto) ->
            ProfileOption(
                icono = icono,
                texto = texto,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            )
        }
    }
}