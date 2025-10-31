package com.levelup.levelupgamer.data

import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenciasUsuarioRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object LlavesPreferencia {
        val ESTA_LOGEADO = booleanPreferencesKey("esta_logeado")
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    }

    // ---- Guardar valores ----

    suspend fun guardarEstadoLogeado(estaLogeado: Boolean) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.ESTA_LOGEADO] = estaLogeado
        }
    }

    suspend fun guardarNombreUsuario(nombreUsuario: String) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.NOMBRE_USUARIO] = nombreUsuario
        }
    }

    // ---- Leer valores ----

    val estaLogeado: Flow<Boolean> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.ESTA_LOGEADO] ?: false
        }

    val nombreUsuario: Flow<String> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.NOMBRE_USUARIO] ?: ""
        }
}