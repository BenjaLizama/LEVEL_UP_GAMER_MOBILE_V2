package com.levelup.levelupgamer.di

import android.content.Context
import androidx.room.Room
import com.levelup.levelupgamer.db.BaseDatos
import com.levelup.levelupgamer.db.dao.CarritoDAO
import com.levelup.levelupgamer.db.dao.ProductoDAO
import com.levelup.levelupgamer.db.dao.UsuarioDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloBaseDatos {

    @Provides
    @Singleton
    fun proveerBaseDatos(
        @ApplicationContext context: Context,
        productoDaoProvider: Provider<ProductoDAO>
    ): BaseDatos {
        val callback = ModuloProducto(productoDaoProvider)
        return Room.databaseBuilder(
            context,
            BaseDatos::class.java,
            "Level Up Gamer"
        ).fallbackToDestructiveMigration().addCallback(callback).build()
    }

    @Provides
    @Singleton
    fun proveerUsuarioDAO(basedatos: BaseDatos): UsuarioDAO {
        return basedatos.getUsuarioDao()
    }

    @Provides
    @Singleton
    fun proveerProductosDao(basedatos: BaseDatos): ProductoDAO {
        return basedatos.getProductoDao()
    }

    @Provides
    @Singleton
    fun proveerCarritoDao(basedatos: BaseDatos): CarritoDAO {
        return basedatos.getCarritoDao()
    }
}