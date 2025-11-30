package com.levelup.levelupgamer.viewmodel.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EventoPerfilUi {
    object NavigateToLogin : EventoPerfilUi()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferenciasRepository: PreferenciasUsuarioRepository
) : ViewModel() {

    private val _mensajesToast = MutableSharedFlow<String>()
    val mensajesToast: SharedFlow<String> = _mensajesToast.asSharedFlow()

    private val _eventoUi = Channel<EventoPerfilUi>()
    val eventoUi = _eventoUi.receiveAsFlow()

    private val _mostrarDialogoImagen = MutableStateFlow(false)
    val mostrarDialogoImagen: StateFlow<Boolean> = _mostrarDialogoImagen.asStateFlow()

    fun setMostrarDialogoImagen(mostrar: Boolean) {
        _mostrarDialogoImagen.value = mostrar
    }

    fun notificarProximamente() {
        viewModelScope.launch {
            _mensajesToast.emit("¡Próximamente!")
        }
    }



    fun cerrarSesion() {
        viewModelScope.launch {
            preferenciasRepository.limpiarDatos()
            _eventoUi.send(EventoPerfilUi.NavigateToLogin)
        }
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