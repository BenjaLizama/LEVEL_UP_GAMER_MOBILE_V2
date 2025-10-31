package com.levelup.levelupgamer.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.levelup.levelupgamer.ui.screens.CartScreen
import com.levelup.levelupgamer.ui.screens.ProfileScreen
import com.levelup.levelupgamer.ui.screens.StoreScreen
import com.levelup.levelupgamer.viewmodel.MainViewModel

@Composable
fun AppNavegacion( viewModel: MainViewModel = hiltViewModel() ) {
    val estaLogueado by viewModel.estaLogueado.collectAsState()
    val controladorNavegacion = rememberNavController()

    if (estaLogueado == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val rutaInicio = if (estaLogueado == true) {
        ROUTES.TIENDA
    } else {
        ROUTES.AUTENTICACION
    }

    NavHost(
        navController = controladorNavegacion,
        startDestination = rutaInicio
    ) {
        authGraph(controladorNavegacion)

        composable(ROUTES.TIENDA) { StoreScreen(controladorNavegacion) }
        composable(ROUTES.CARRITO) { CartScreen(controladorNavegacion) }
        composable(ROUTES.PERFIL) { ProfileScreen(controladorNavegacion) }
    }
}