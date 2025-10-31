package com.levelup.levelupgamer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.levelup.levelupgamer.ui.screens.CartScreen
import com.levelup.levelupgamer.ui.screens.ProfileScreen
import com.levelup.levelupgamer.ui.screens.StoreScreen

@Composable
fun AppNavegacion() {
    val controladorNavegacion = rememberNavController()

    NavHost(
        navController = controladorNavegacion,
        startDestination = ROUTES.AUTENTICACION
    ) {
        authGraph(controladorNavegacion)

        composable(ROUTES.TIENDA) { StoreScreen(controladorNavegacion) }
        composable(ROUTES.CARRITO) { CartScreen(controladorNavegacion) }
        composable(ROUTES.PERFIL) { ProfileScreen(controladorNavegacion) }
    }
}