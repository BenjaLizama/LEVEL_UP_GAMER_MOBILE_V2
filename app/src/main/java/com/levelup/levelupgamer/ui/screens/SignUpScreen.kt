package com.levelup.levelupgamer.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.levelup.levelupgamer.navigation.ROUTES
import com.levelup.levelupgamer.ui.components.atoms.CustomOutlinedTextField
import com.levelup.levelupgamer.ui.theme.ColorAcento
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.ui.theme.ColorTextoPrincipal
import com.levelup.levelupgamer.viewmodel.autenticacion.AutenticacionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    autenticacionViewModel: AutenticacionViewModel,
    controladorNavegacion: NavHostController
) {

    val creacionExitosa by autenticacionViewModel.creacionExitosa.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val estado by autenticacionViewModel.uiState.collectAsState()

    val isLoading = estado.isLoading
    val mensajeError = estado.mensajeError
    val nombre = estado.nombre
    val nombreError = estado.nombreError
    val apellido = estado.apellido
    val apellidoError = estado.apellidoError
    val correo = estado.correo
    val correoError = estado.correoError
    val contrasena = estado.contrasena
    val contrasenaError = estado.contrasenaError
    val confirmarContrasena = estado.confirmarContrasena
    val confirmarContrasenaError = estado.confirmarContrasenaError

    LaunchedEffect(key1 = creacionExitosa) {
        if (creacionExitosa) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Cuenta creada exitosamente",
                    duration = SnackbarDuration.Long
                )
            }
            delay(1000)
            controladorNavegacion.navigate(ROUTES.TIENDA)
        }
    }

    LaunchedEffect(key1 = mensajeError) {
        mensajeError?.let {mensaje ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = mensaje,
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    Scaffold ( snackbarHost = { SnackbarHost(snackbarHostState) } ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(ColorFondo)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Crear cuenta",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 40.sp,
                color = ColorTextoPrincipal
            )

            Spacer(Modifier.height(50.dp))

            Row {
                CustomOutlinedTextField(
                    value = nombre,
                    onValueChange = { nuevoTexto ->
                        autenticacionViewModel.actualizarNombre(nuevoTexto)
                    },
                    label = "Nombre",
                    mensajeError = nombreError,
                    modifier = Modifier
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(6.dp))

                CustomOutlinedTextField(
                    value = apellido,
                    onValueChange = { nuevoTexto ->
                        autenticacionViewModel.actualizarApellido(nuevoTexto)
                    },
                    label = "Apellido",
                    mensajeError = apellidoError,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            CustomOutlinedTextField(
                value = correo,
                onValueChange = { nuevoTexto ->
                    autenticacionViewModel.actualizarCorreo(nuevoTexto)
                },
                label = "Correo electronico",
                mensajeError = correoError,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            CustomOutlinedTextField(
                value = contrasena,
                onValueChange = { nuevoTexto ->
                    autenticacionViewModel.actualizarContrasena(nuevoTexto)
                },
                label = "Contraseña",
                mensajeError = contrasenaError,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            CustomOutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { nuevoTexto ->
                    autenticacionViewModel.actualizarConfirmarContrasena(nuevoTexto)
                },
                label = "Confirmar contraseña",
                mensajeError = confirmarContrasenaError,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row {
                OutlinedButton (
                    onClick = { controladorNavegacion.navigate(ROUTES.LOGIN) {
                        popUpTo(controladorNavegacion.graph.id) {
                            inclusive = true
                        }
                    } },
                    enabled = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, ColorAcento),
                ) {
                    Text("Acceder", color = ColorAcento)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { autenticacionViewModel.crearCuenta() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorAcento
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (estado.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Registrarme")
                    }
                }
            }
        }
    }

}