package com.levelup.levelupgamer.ui.screens

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog // 👈 NECESARIO
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.levelup.levelupgamer.ui.components.organisms.UserProfile
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.utils.crearUriTemporal
import com.levelup.levelupgamer.viewmodel.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    controladorNavegacion: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val nombre by viewModel.nombreUsuario.collectAsState()
    val apellido by viewModel.apellidoUsuario.collectAsState()
    val correo by viewModel.correoUsuario.collectAsState()
    val imagen by viewModel.imagenUri.collectAsState()
    val mostrarDialogo by viewModel.mostrarDialogoImagen.collectAsState()

    val uriParaCamara = remember { crearUriTemporal(context) }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.setMostrarDialogoImagen(false)
            if (uri != null) {
                viewModel.actualizarImagenPerfil(uri)
            }
        }
    )

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { exito ->
            viewModel.setMostrarDialogoImagen(false)
            if (exito) {
                viewModel.actualizarImagenPerfil(uriParaCamara)
            }
        }
    )

    val permisoCamaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                camaraLauncher.launch(uriParaCamara)
            } else {
                // Manejar el caso en que el usuario niega el permiso
            }
        }
    )

    Scaffold { padding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .background(ColorFondo)
            .padding(padding)
        ) {
            UserProfile(nombre, apellido, correo, imagen, onImagenClick = {
                viewModel.setMostrarDialogoImagen(true)
            })
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { viewModel.setMostrarDialogoImagen(false) },
            title = { Text("Cambiar Imagen de Perfil") },
            text = { Text("Selecciona la fuente para tu nueva imagen.") },

            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.setMostrarDialogoImagen(false)
                        galeriaLauncher.launch("image/*")
                    }
                ) { Text("Galería") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.setMostrarDialogoImagen(false)

                        when (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)) {
                            PackageManager.PERMISSION_GRANTED -> {
                                camaraLauncher.launch(uriParaCamara)
                            }
                            else -> {
                                permisoCamaraLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                        }
                    }
                ) { Text("Cámara") }
            }
        )
    }
}