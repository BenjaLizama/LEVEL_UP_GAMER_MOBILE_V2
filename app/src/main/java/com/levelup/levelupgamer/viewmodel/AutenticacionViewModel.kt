package com.levelup.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.db.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

@HiltViewModel
class AutenticacionViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    private val _creacionExitosa = MutableStateFlow(false) // => Almacena el valor y emite actualizaciones
    val creacionExitosa: StateFlow<Boolean> = _creacionExitosa.asStateFlow() // 2. StateFlow publico para la UI

    fun crearCuenta(usuario: Usuario) {
        viewModelScope.launch {
            try {

                _isLoading.value = true

                val existe = usuarioRepository.buscarUsuarioPorCorreo(usuario.correo)

                if (existe) {
                    _isLoading.value = false
                    _mensajeError.value = "El correo ya esta registrado."
                    return@launch
                }

                val id = usuarioRepository.crearCuenta(usuario)
                _isLoading.value = false
                _creacionExitosa.value = true

            } catch (e: Exception) {
                _mensajeError.value = "Error al crear la cuenta."
            } finally {
                _isLoading.value = false
            }
        }
    }
}