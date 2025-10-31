package com.levelup.levelupgamer.ui.components.atoms

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SimpleIcon(
    icono: ImageVector,
    tamano: Dp = 24.dp,
    color: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icono,
        contentDescription = null,
        tint = color,
        modifier = modifier.size(tamano)
    )
}