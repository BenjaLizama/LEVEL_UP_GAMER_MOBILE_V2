package com.levelup.levelupgamer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.ui.navigation.ROUTES
import com.levelup.levelupgamer.viewmodel.AutenticacionViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    autenticacionViewModel: AutenticacionViewModel,
    controladorNavegacion: NavHostController
) {
    val isLoading by autenticacionViewModel.isLoading.collectAsState()
    val creacionExitosa by autenticacionViewModel.creacionExitosa.collectAsState()
    val mensajeError by autenticacionViewModel.mensajeError.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val usuario = Usuario(1L, "Benjamin", "Lizama", "benjamin@gmail.com", "123456")

    LaunchedEffect(key1 = creacionExitosa) {
        if (creacionExitosa) {
            controladorNavegacion.navigate(ROUTES.TIENDA)
        }
    }

    LaunchedEffect(key1 = mensajeError) {
        mensajeError?.let {mensaje ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = mensaje,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold ( snackbarHost = { SnackbarHost(snackbarHostState) } ) { padding ->
        Column (modifier = Modifier.padding(16.dp)) {

            Button(
                onClick = { autenticacionViewModel.crearCuenta(usuario) },
                enabled = !isLoading
            ) {
                Text(text = if (isLoading) "Cargando..." else "Crear cuenta")
            }
        }
    }

}