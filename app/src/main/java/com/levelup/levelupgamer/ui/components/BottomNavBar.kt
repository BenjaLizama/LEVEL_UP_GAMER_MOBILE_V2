package com.levelup.levelupgamer.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.levelup.levelupgamer.navigation.itemsNavegacion
import com.levelup.levelupgamer.ui.theme.ColorBarraNavegacion
import com.levelup.levelupgamer.ui.theme.ColorFondo
import com.levelup.levelupgamer.ui.theme.ColorTextoSecundario

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    NavigationBar (
        containerColor = ColorBarraNavegacion
    ) {
        itemsNavegacion.forEach { item ->
            val estaSeleccionado = item.ruta == rutaActual

            NavigationBarItem(
                selected = estaSeleccionado,
                onClick = {
                    navController.navigate(item.ruta) {
                        navController.graph.startDestinationRoute?.let { ruta ->
                            popUpTo(ruta) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(item.icono, contentDescription = item.titulo)
                },
                label = {
                    Text(item.titulo)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = ColorFondo,
                    selectedIconColor = ColorTextoSecundario,
                    selectedTextColor = ColorTextoSecundario
                )
            )
        }
    }

}