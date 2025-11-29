package com.levelup.levelupgamer.db.entidades

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "TABLA_CARRITO",
    // Estas propiedades coinciden con tu data class
    primaryKeys = ["idUsuario", "idProducto"],
    foreignKeys = [
        ForeignKey(
            entity = Producto::class,
            // ¡¡ARREGLADO!!
            // Esta es la PK de tu 'Producto.kt'
            parentColumns = ["id"],
            // Esta es la FK de tu 'Carrito.kt'
            childColumns = ["idProducto"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            // Esta es la PK de tu 'UsuarioRetornoDTO.kt' (¡Correcto!)
            parentColumns = ["idUsuario"],
            // Esta es la FK de tu 'Carrito.kt' (¡Correcto!)
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Carrito(
    val idUsuario: Long,
    val idProducto: Long,
    val cantidad: Int = 1
)
