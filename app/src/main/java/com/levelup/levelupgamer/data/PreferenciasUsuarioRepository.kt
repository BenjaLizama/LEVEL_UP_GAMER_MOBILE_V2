package com.levelup.levelupgamer.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
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
        val ESTA_LOGUEADO = booleanPreferencesKey("esta_logeado")
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
        val APELLIDO_USUARIO = stringPreferencesKey("apellido_usuario")
        val CORREO_USUARIO = stringPreferencesKey("correo_usuario")
        val IMAGEN_PERFIL = stringPreferencesKey("imagen_perfil")

        val ID_USUARIO = longPreferencesKey("id_usuario")
    }

    // ---- Guardar valores ----

    suspend fun guardarEstadoLogueado(estaLogeado: Boolean) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.ESTA_LOGUEADO] = estaLogeado
        }
    }

    suspend fun guardarNombreUsuario(nombreUsuario: String) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.NOMBRE_USUARIO] = nombreUsuario
        }
    }

    suspend fun guardarApellidoUsuario(apellidoUsuario: String) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.APELLIDO_USUARIO] = apellidoUsuario
        }
    }

    suspend fun guardarCorreoUsuario(correoUsuario: String) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.CORREO_USUARIO] = correoUsuario
        }
    }

    suspend fun guardarImagenPerfil(imagenPefil: String) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.IMAGEN_PERFIL] = imagenPefil
        }
    }
    suspend fun guardarIdUsuario(id: Long) {
        dataStore.edit { preferencias ->
            preferencias[LlavesPreferencia.ID_USUARIO] = id
        }
    }
    suspend fun limpiarDatos() {
        dataStore.edit { preferencias ->
            preferencias.clear()
        }
    }



    // ---- Leer valores ----

    val estaLogueado: Flow<Boolean> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.ESTA_LOGUEADO] ?: false
        }

    val nombreUsuario: Flow<String> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.NOMBRE_USUARIO] ?: ""
        }

    val apellidoUsuario: Flow<String> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.APELLIDO_USUARIO] ?: ""
        }

    val correoUsuario: Flow<String> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.CORREO_USUARIO] ?: ""
        }

    val imagenPerfil: Flow<String> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.IMAGEN_PERFIL] ?: ""
        }
    val idUsuario: Flow<Long> = dataStore.data
        .map { preferencias ->
            preferencias[LlavesPreferencia.ID_USUARIO] ?: 0L
        }
}