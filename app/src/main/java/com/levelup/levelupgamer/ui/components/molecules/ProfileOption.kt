package com.levelup.levelupgamer.ui.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levelup.levelupgamer.ui.components.atoms.SimpleIcon
import com.levelup.levelupgamer.ui.theme.ColorTextoPrincipal
import com.levelup.levelupgamer.utils.ICONOS


data class PerfilOpcion(
    val icono: ImageVector,
    val texto: String,
    val onClick: () -> Unit
)

@Composable
fun ProfileOption(
    icono: ImageVector,
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .clickable { onClick() },
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleIcon(
                icono = icono,
                tamano = 25.dp,
                color = ColorTextoPrincipal
            )

            Text(
                text = texto,
                fontSize = 25.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            )

            SimpleIcon(
                icono = ICONOS.RightArrow,
                tamano = 25.dp,
                color = ColorTextoPrincipal
            )
        }
    }
}