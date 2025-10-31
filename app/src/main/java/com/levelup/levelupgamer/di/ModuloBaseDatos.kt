package com.levelup.levelupgamer.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.levelup.levelupgamer.db.BaseDatos
import com.levelup.levelupgamer.db.dao.UsuarioDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuloBaseDatos {

    // Esto le dice a Hilt como crear la instancia de la base de datos.
    @Provides
    @Singleton
    fun proveerBaseDatos(@ApplicationContext context: Context): BaseDatos {
        return Room.databaseBuilder(
            context,
            BaseDatos::class.java,
            "Level Up Gamer"
        ).build()
    }

    // Esto se encarga de proveer el DAO
    @Provides
    @Singleton
    fun proveerUsuarioDAO(basedatos: BaseDatos): UsuarioDAO {
        return basedatos.usuarioDao()
    }
}