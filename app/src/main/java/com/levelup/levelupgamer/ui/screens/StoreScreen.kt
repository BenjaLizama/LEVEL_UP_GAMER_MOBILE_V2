package com.levelup.levelupgamer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.levelup.levelupgamer.ui.theme.ColorFondo

@Composable
fun StoreScreen(controladorNavegacion: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorFondo)
    ) {
        ProductScreen()
    }
}