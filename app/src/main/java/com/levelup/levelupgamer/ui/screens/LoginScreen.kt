package com.levelup.levelupgamer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavHostController
import com.levelup.levelupgamer.navigation.ROUTES
import com.levelup.levelupgamer.ui.components.CustomOutlinedTextField
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.ui.theme.ColorTextoPrincipal
import com.levelup.levelupgamer.viewmodel.autenticacion.AutenticacionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    autenticacionViewModel: AutenticacionViewModel,
    controladorNavegacion: NavHostController
) {
    val estado by autenticacionViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val inicioExitoso by autenticacionViewModel.inicioExitoso.collectAsState()

    val correo = estado.correoInicio
    val contrasena = estado.contrasenaInicio
    val correoError = estado.correoErrorInicio
    val contrasenaError = estado.contrasenaErrorInicio
    val isLoading = estado.isLoading
    val mensajeError = estado.mensajeError

    LaunchedEffect(key1 = inicioExitoso) {
        if (inicioExitoso) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Inicio de sesion exitoso",
                    duration = SnackbarDuration.Long
                )
                delay(1000)
                controladorNavegacion.navigate(ROUTES.TIENDA)
            }
        }
    }

    LaunchedEffect(key1 = mensajeError) {
        if (mensajeError != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = mensajeError,
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
                style = MaterialTheme.typography.headlineMedium,
                color = ColorTextoPrincipal
            )

            Spacer(modifier = Modifier.height(6.dp))

            CustomOutlinedTextField(
                value = correo,
                onValueChange = { nuevoTexto ->
                    autenticacionViewModel.actualizarCorreoInicio(nuevoTexto)
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
                    autenticacionViewModel.actualizarContrasenaInicio(nuevoTexto)
                },
                label = "Contraseña",
                mensajeError = contrasenaError,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = { autenticacionViewModel.iniciarSesion() },
                enabled = !isLoading
            ) {
                if (isLoading) CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                ) else {
                    Text(text = "Iniciar sesión")
                }
            }
        }
    }
}