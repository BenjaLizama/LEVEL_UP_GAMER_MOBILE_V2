package com.levelup.levelupgamer.ui.components.organisms

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.levelup.levelupgamer.R
import com.levelup.levelupgamer.ui.theme.ColorTextoPrincipal

@Composable
fun UserProfile(
    nombre: String,
    apellido: String,
    correo: String,
    imagenPerfil: Uri?,
    onImagenClick: () -> Unit
) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box {
                AsyncImage(
                    model = imagenPerfil,
                    contentDescription = "Imagen de $nombre $apellido",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .clickable { onImagenClick() },
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.no_profile_image),
                    placeholder = painterResource(id = R.drawable.no_profile_image)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text("$nombre $apellido", color = ColorTextoPrincipal, fontSize = 25.sp)
            Text(correo, color = ColorTextoPrincipal)
        }
    }
}