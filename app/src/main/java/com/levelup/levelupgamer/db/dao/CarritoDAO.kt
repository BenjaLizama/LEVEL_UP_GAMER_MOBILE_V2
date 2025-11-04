package com.levelup.levelupgamer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.levelup.levelupgamer.db.entidades.Carrito
import com.levelup.levelupgamer.db.entidades.CarritoItemConDetalles
import com.levelup.levelupgamer.db.entidades.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun agregarAlCarrito(carrito: Carrito)

    @Query(
        """
        UPDATE TABLA_CARRITO 
        SET cantidad = cantidad + 1 
        WHERE idProducto = :idProducto AND idUsuario = :idUsuario
    """
    )
    suspend fun aumentarCantidad(idProducto: Long, idUsuario: Long)

    @Query("""
    SELECT 
        T_PROD.*, 
        T_CARRITO.cantidad 
    FROM TABLA_PRODUCTO AS T_PROD
    INNER JOIN TABLA_CARRITO AS T_CARRITO 
        ON T_PROD.id = T_CARRITO.idProducto
    WHERE T_CARRITO.idUsuario = :idusuario 
""")
    fun getItemsDelCarritoConDetalles(idusuario: Long): Flow<List<CarritoItemConDetalles>>

    @Query("SELECT * FROM TABLA_CARRITO WHERE idUsuario = :idUsuario AND idProducto = :idProducto")
    suspend fun obtenerItem(idUsuario: Long, idProducto: Long): Carrito?
    @Query("""
        UPDATE TABLA_CARRITO 
        SET cantidad = cantidad - 1 
        WHERE idProducto = :idProducto AND idUsuario = :idUsuario
    """)
    suspend fun disminuirCantidad(idProducto: Long, idUsuario: Long)

    @Query("DELETE FROM TABLA_CARRITO WHERE idProducto = :idProducto AND idUsuario = :idUsuario")
    suspend fun eliminarItem(idProducto: Long, idUsuario: Long)

    @Query("DELETE FROM TABLA_CARRITO WHERE idUsuario = :idUsuario")
    suspend fun vaciarCarrito(idUsuario: Long)
}