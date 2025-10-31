package com.levelup.levelupgamer.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.levelup.levelupgamer.ui.components.BottomNavBar
import com.levelup.levelupgamer.ui.screens.CartScreen
import com.levelup.levelupgamer.ui.screens.ProfileScreen
import com.levelup.levelupgamer.ui.screens.StoreScreen
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.viewmodel.MainViewModel

@Composable
fun AppNavegacion( viewModel: MainViewModel = hiltViewModel() ) {
    val estaLogueado by viewModel.estaLogueado.collectAsState()
    val controladorNavegacion = rememberNavController()

    val pilaNavegacion by controladorNavegacion.currentBackStackEntryAsState()
    val rutaActual = pilaNavegacion?.destination?.route

    val pantallasConBarraNavegacion = setOf(
        ROUTES.TIENDA,
        ROUTES.CARRITO,
        ROUTES.PERFIL
    )

    val mostrarBarra = rutaActual in pantallasConBarraNavegacion

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

    Scaffold (
        bottomBar = {
            if (mostrarBarra) {
                BottomNavBar(navController = controladorNavegacion)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = controladorNavegacion,
            startDestination = rutaInicio,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            authGraph(controladorNavegacion)

            composable(ROUTES.TIENDA) { StoreScreen(controladorNavegacion) }
            composable(ROUTES.CARRITO) { CartScreen(controladorNavegacion) }
            composable(ROUTES.PERFIL) { ProfileScreen(controladorNavegacion) }
        }
    }


}