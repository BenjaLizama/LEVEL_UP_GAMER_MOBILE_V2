package com.levelup.levelupgamer.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.levelup.levelupgamer.ui.screens.LoginScreen
import com.levelup.levelupgamer.ui.screens.SignUpScreen

import com.levelup.levelupgamer.viewmodel.autenticacion.AutenticacionViewModel


fun NavGraphBuilder.authGraph(controladorNavegacion: NavHostController) {

    navigation(
        startDestination = ROUTES.SIGN_UP,
        route = ROUTES.AUTENTICACION
    ) {

        composable(ROUTES.LOGIN) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                controladorNavegacion.getBackStackEntry(ROUTES.AUTENTICACION)
            }

            val autenticacionViewModel: AutenticacionViewModel = hiltViewModel(parentEntry)

            LoginScreen(autenticacionViewModel = autenticacionViewModel, controladorNavegacion = controladorNavegacion)
        }

        composable(ROUTES.SIGN_UP) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                controladorNavegacion.getBackStackEntry(ROUTES.AUTENTICACION)
            }

            val autenticacionViewModel: AutenticacionViewModel = hiltViewModel(parentEntry)

            SignUpScreen(autenticacionViewModel = autenticacionViewModel, controladorNavegacion = controladorNavegacion)
        }
    }
}
