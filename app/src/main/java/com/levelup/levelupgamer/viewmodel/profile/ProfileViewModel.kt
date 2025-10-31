package com.levelup.levelupgamer.viewmodel.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferenciasRepository: PreferenciasUsuarioRepository
) : ViewModel() {

    private val _mostrarDialogoImagen = MutableStateFlow(false)
    val mostrarDialogoImagen: StateFlow<Boolean> = _mostrarDialogoImagen.asStateFlow()

    private val _imagenUri = MutableStateFlow<Uri?>(null)

    fun setMostrarDialogoImagen(mostrar: Boolean) {
        _mostrarDialogoImagen.value = mostrar
    }

    val imagenUri: StateFlow<Uri?> = preferenciasRepository.imagenPerfil
        .map { uriString ->
            if (uriString.isNullOrEmpty()) null
            else Uri.parse(uriString)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = null
        )
    fun actualizarImagenPerfil(uri: Uri) {
        viewModelScope.launch {
            preferenciasRepository.guardarImagenPerfil(uri.toString())
        }
    }

    val nombreUsuario: StateFlow<String> = preferenciasRepository.nombreUsuario
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = "Cargando..."
        )

    val apellidoUsuario: StateFlow<String> = preferenciasRepository.apellidoUsuario
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = "Cargando..."
        )

    val correoUsuario: StateFlow<String> = preferenciasRepository.correoUsuario
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = "Cargando..."
        )
}