package com.levelup.levelupgamer.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context

// Nombre del archivo donde DataStore guardara los datos.
private const val PREFERENCIAS_USUARIO = "preferencias_usuario"

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCIAS_USUARIO
)