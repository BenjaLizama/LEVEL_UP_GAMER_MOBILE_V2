package com.levelup.levelupgamer.viewmodel.autenticacion

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.db.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

@HiltViewModel
class AutenticacionViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoFormularioUI())
    val uiState: StateFlow<EstadoFormularioUI> = _uiState.asStateFlow()


    fun crearCuenta() {
        viewModelScope.launch {
            try {

                val estado = _uiState.value
                _uiState.update { it.copy(isLoading = true) }

                if (!validarFormulario()) {
                    _uiState.update { it.copy(mensajeError = "Por favor, corrige los errores en el formulario.") }
                    return@launch
                }

                val nuevoUsuario = Usuario(
                    idUsuario = 0L,
                    nombre = estado.nombre,
                    apellido = estado.apellido,
                    correo = estado.correo,
                    contrasena = estado.contrasena
                )

                val existe = usuarioRepository.buscarUsuarioPorCorreo(nuevoUsuario.correo)

                if (existe) {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiState.update { it.copy(mensajeError = "El correo ya esta registrado.") }
                    return@launch
                }

                val id = usuarioRepository.crearCuenta(nuevoUsuario)
                _uiState.update { it.copy(isLoading = false) }
                _creacionExitosa.value = true

            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al crear la cuenta.") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun validarFormulario(): Boolean {
        _uiState.update {
            it.copy(
                nombreError = null,
                apellidoError = null,
                correoError = null,
                contrasenaError = null,
                confirmarContrasenaError = null
            )
        }

        var esValido = true
        val estado = _uiState.value


        if (estado.nombre.isBlank() || estado.nombre.length < 3) {
            _uiState.update { it.copy(nombreError = "Mínimo 3 caracteres.") }
            esValido = false
        }

        if (estado.apellido.trim().isBlank() || estado.apellido.trim().length < 3) {
            _uiState.update { it.copy(apellidoError = "Mínimo 3 caracteres.") }
            esValido = false
        }

        if (estado.correo.trim().isBlank()) {
            _uiState.update { it.copy(correoError = "El correo no puede estar vacío.") }
            esValido = false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(estado.correo).matches()) {
            _uiState.update { it.copy(correoError = "Formato de correo inválido.") }
            esValido = false
        }

        if (estado.contrasena.trim().isBlank() || estado.contrasena.trim().length < 6) {
            _uiState.update { it.copy(contrasenaError = "Mínimo 6 caracteres.") }
            esValido = false
        }

        if (estado.confirmarContrasena != estado.contrasena) {
            _uiState.update { it.copy(confirmarContrasenaError = "Las contraseñas no coinciden.") }
            esValido = false
        }
        else if (estado.confirmarContrasena.trim().isBlank()) {
            _uiState.update { it.copy(confirmarContrasenaError = "El campo no puede estar vacío.") }
            esValido = false
        }


        return esValido
    }

    fun actualizarNombre(nuevoNombre: String) {
        _uiState.update { it.copy(nombre = nuevoNombre) }
    }

    fun actualizarApellido(nuevoApellido: String) {
        _uiState.update { it.copy(apellido = nuevoApellido) }
    }

    fun actualizarCorreo(nuevoCorreo: String) {
        _uiState.update { it.copy(correo = nuevoCorreo) }
    }

    fun actualizarContrasena(nuevaContrasena: String) {
        _uiState.update { it.copy(contrasena = nuevaContrasena) }
    }

    fun actualizarConfirmarContrasena(nuevaConfirmarContrasena: String) {
        _uiState.update { it.copy(confirmarContrasena = nuevaConfirmarContrasena) }
    }

    private val _creacionExitosa =
        MutableStateFlow(false) // => Almacena el valor y emite actualizaciones
    val creacionExitosa: StateFlow<Boolean> = _creacionExitosa.asStateFlow() // => Expone StateFlow publico para la UI

}