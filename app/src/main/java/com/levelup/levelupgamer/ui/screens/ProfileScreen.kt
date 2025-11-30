package com.levelup.levelupgamer.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.levelup.levelupgamer.navigation.ROUTES
import com.levelup.levelupgamer.ui.components.molecules.ContainerProfileOptions
import com.levelup.levelupgamer.ui.components.molecules.PerfilOpcion
import com.levelup.levelupgamer.ui.components.organisms.UserProfile
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.utils.ICONOS
import com.levelup.levelupgamer.utils.crearUriTemporal
import com.levelup.levelupgamer.viewmodel.profile.EventoPerfilUi
import com.levelup.levelupgamer.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()

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

    LaunchedEffect(key1 = true) {
        viewModel.eventoUi.collect { evento ->
            when(evento) {
                is EventoPerfilUi.NavigateToLogin -> {
                    scope.launch {
                        controladorNavegacion.navigate(ROUTES.AUTENTICACION) {
                            popUpTo(controladorNavegacion.graph.id) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.mensajesToast.collect { mensaje ->
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold { padding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .background(ColorFondo)
            .padding(padding)
        ) {

            Spacer(Modifier.height(20.dp))

            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp
                )
            ) {
                item {
                    UserProfile(
                        nombre,
                        apellido,
                        correo,
                        imagen,
                        onImagenClick = {
                        viewModel.setMostrarDialogoImagen(true)
                    })
                }

                item {
                    Spacer(Modifier.height(15.dp))
                    ContainerProfileOptions(
                        opciones = listOf(
                            PerfilOpcion(
                                icono = ICONOS.Heart,
                                texto = "Favoritos",
                                onClick = { viewModel.notificarProximamente() }
                            ),
                            PerfilOpcion(
                                icono = ICONOS.GamePad,
                                texto = "Biblioteca",
                                onClick = { viewModel.notificarProximamente() }
                            ),
                            PerfilOpcion(
                                icono = ICONOS.Badge,
                                texto = "Pedidos",
                                onClick = { viewModel.notificarProximamente() }
                            )
                        ),
                        texto = "Mi cuenta"
                    )
                }

                item {
                    Spacer(Modifier.height(15.dp))
                    ContainerProfileOptions(
                        opciones = listOf(
                            PerfilOpcion(
                                icono = ICONOS.Gift,
                                texto = "Canjear",
                                onClick = { viewModel.notificarProximamente() }
                            ),
                            PerfilOpcion(
                                icono = ICONOS.Wallet,
                                texto = "Añadir fondos",
                                onClick = { viewModel.notificarProximamente() }
                            )
                        ),
                        texto = "Saldo"
                    )
                }

                item {
                    Spacer(Modifier.height(15.dp))
                    ContainerProfileOptions(
                        opciones = listOf(
                            PerfilOpcion(
                                icono = ICONOS.Badge,
                                texto = "Mi información",
                                onClick = { viewModel.notificarProximamente() }
                            ),
                            PerfilOpcion(
                                icono = ICONOS.Key,
                                texto = "Cambiar contraseña",
                                onClick = { viewModel.notificarProximamente() }
                            )
                        ),
                        texto = "Mi perfil"
                    )
                }

                item {
                    Spacer(Modifier.height(15.dp))
                    ContainerProfileOptions(
                        opciones = listOf(
                            PerfilOpcion(
                                icono = ICONOS.Warning,
                                texto = "Soporte",
                                onClick = { viewModel.notificarProximamente() }
                            )
                        ),
                        texto = "Soporte"
                    )
                }

                item {
                    Spacer(Modifier.height(15.dp))
                    ContainerProfileOptions(
                        opciones = listOf(
                            PerfilOpcion(
                                icono = ICONOS.Language,
                                texto = "Idioma",
                                onClick = { viewModel.notificarProximamente() }
                            ),
                            PerfilOpcion(
                                icono = ICONOS.Settings,
                                texto = "Configuración",
                                onClick = { viewModel.notificarProximamente() }
                            ),
                            PerfilOpcion(
                                icono = ICONOS.SignOut,
                                texto = "Cerrar sesión",
                                onClick = {
                                    viewModel.cerrarSesion()
                                }
                            )
                        ),
                        texto = "Soporte"
                    )
                }
            }
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

                        when (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)) {
                            PackageManager.PERMISSION_GRANTED -> {
                                camaraLauncher.launch(uriParaCamara)
                            }
                            else -> {
                                permisoCamaraLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    }
                ) { Text("Cámara") }
            }
        )
    }
}