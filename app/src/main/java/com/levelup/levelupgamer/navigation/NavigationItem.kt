package com.levelup.levelupgamer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val titulo: String,
    val icono: ImageVector,
    val ruta: String
)

val itemsNavegacion = listOf(
    NavigationItem("Tienda", Icons.Filled.Home, "tienda"),
    NavigationItem("Carrito", Icons.Filled.ShoppingCart, "carrito"),
    NavigationItem("Perfil", Icons.Filled.Person, "perfil")

)
