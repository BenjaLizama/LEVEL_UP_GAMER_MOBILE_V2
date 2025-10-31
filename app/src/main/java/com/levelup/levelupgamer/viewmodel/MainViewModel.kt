package com.levelup.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val preferenciasRepository: PreferenciasUsuarioRepository
) : ViewModel() {

    val estaLogueado: StateFlow<Boolean?> = preferenciasRepository.estaLogueado
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null
        )

    fun cerrarSesion() {
        viewModelScope.launch {
            preferenciasRepository.guardarEstadoLogueado(false)
            preferenciasRepository.guardarNombreUsuario("")
        }
    }
}