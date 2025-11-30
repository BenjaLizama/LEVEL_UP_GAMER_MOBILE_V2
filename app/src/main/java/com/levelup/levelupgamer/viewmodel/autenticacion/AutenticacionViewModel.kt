package com.levelup.levelupgamer.viewmodel.autenticacion

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import com.levelup.levelupgamer.db.repository.UsuarioRepository
import com.levelup.levelupgamer.model.usuarios.AgregarUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.IniciarSesionDTO
import com.levelup.levelupgamer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AutenticacionViewModel @Inject constructor(
    private val preferenciasRepository: PreferenciasUsuarioRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoFormularioUI())
    val uiState: StateFlow<EstadoFormularioUI> = _uiState.asStateFlow()

    fun crearCuenta() {
        viewModelScope.launch {
            val estado = uiState.value

            if (!validarFormulario()) {
                println("Debes revisar los campos antes de continuar")
                return@launch
            }

            val usuarioAgregado = AgregarUsuarioDTO(
                correo = estado.correo,
                contrasena = estado.contrasena,
                nombre = estado.nombre,
                apellido = estado.apellido,
                fechaNacimiento = "2000-05-05",
                nombreUsuario = estado.nombre + estado.apellido
            )

            val resultado = userRepository.agregarUsuario(usuarioAgregado)

            if (resultado != null) {
                println("Usuario creado con exito: ${estado.nombre}")
                preferenciasRepository.guardarIdUsuario(resultado.idUsuario)
                preferenciasRepository.guardarNombreUsuario(resultado.nombre)
                preferenciasRepository.guardarApellidoUsuario(resultado.apellido)
                preferenciasRepository.guardarCorreoUsuario(resultado.correo)
                preferenciasRepository.guardarEstadoLogueado(true)
                preferenciasRepository.guardarImagenPerfil(resultado.imagenPerfilURL)

                _creacionExitosa.value = true
            } else {
                println("Fallo la creacion del usuario: ${estado.nombre}")
            }
        }
    }

    fun iniciarSesion() {
        viewModelScope.launch {
            val estado = uiState.value

            if (!validarFormularioInicio()) {
                println("Error al iniciar sesion")
                return@launch
            }

            val credenciales = IniciarSesionDTO(
                correo = estado.correoInicio,
                contrasena = estado.contrasenaInicio
            )

            val resultado = userRepository.iniciarSesion(credenciales)

            if (resultado != null) {
                println("Usuario logeado con exito!")

                preferenciasRepository.guardarIdUsuario(resultado.idUsuario)
                preferenciasRepository.guardarNombreUsuario(resultado.nombre)
                preferenciasRepository.guardarApellidoUsuario(resultado.apellido)
                preferenciasRepository.guardarCorreoUsuario(resultado.correo)
                preferenciasRepository.guardarImagenPerfil(resultado.imagenPerfilURL)
                preferenciasRepository.guardarEstadoLogueado(true)

                _creacionExitosa.value = true
            } else {
                println("Fallo al iniciar sesion")
            }
        }
    }

    fun validarFormulario(): Boolean {
        _uiState.update {
            it.copy(
                contrasenaError = null,
                confirmarContrasenaError = null
            )
        }

        var esValido = true
        val estado = _uiState.value

        if (estado.contrasena.trim().isBlank() || estado.contrasena.trim().length < 6) {
            _uiState.update { it.copy(contrasenaError = "Mínimo 6 caracteres.") }
            esValido = false
        }

        if (estado.confirmarContrasena.trim().isBlank()) {
            _uiState.update { it.copy(confirmarContrasenaError = "El campo no puede estar vacío.") }
            esValido = false
        }
        else if (estado.confirmarContrasena != estado.contrasena) {
            _uiState.update { it.copy(confirmarContrasenaError = "Las contraseñas no coinciden.") }
            esValido = false
        }

        if (estado.nombreError != null || estado.apellidoError != null || estado.correoError != null) {
            esValido = false
        }


        return esValido
    }

    fun validarFormularioInicio(): Boolean {
        _uiState.update {
            it.copy(
                correoErrorInicio = null,
                contrasenaErrorInicio = null,
            )
        }

        var esValido = true
        val estado = _uiState.value

        if (estado.contrasenaInicio.trim().isBlank() || estado.contrasenaInicio.trim().length < 6) {
            _uiState.update { it.copy(contrasenaErrorInicio = "Mínimo 6 caracteres.") }
            esValido = false
        }

        if (estado.correoErrorInicio != null || estado.contrasenaErrorInicio != null ) {
            esValido = false
        }

        return esValido
    }

    fun actualizarNombre(nuevoNombre: String) {
        _uiState.update {
            it.copy(
                nombre = nuevoNombre,
                nombreError = if (nuevoNombre.isBlank() || nuevoNombre.length < 3)
                    "Mínimo 3 caracteres."
                else null
            )
        }
    }

    fun actualizarApellido(nuevoApellido: String) {
        _uiState.update {
            it.copy(
                apellido = nuevoApellido,
                apellidoError = if (nuevoApellido.trim().isBlank() || nuevoApellido.trim().length < 3)
                    "Mínimo 3 caracteres."
                else null
            )
        }
    }

    fun actualizarCorreo(nuevoCorreo: String) {
        _uiState.update {
            it.copy(
                correo = nuevoCorreo,
                correoError = when {
                    nuevoCorreo.trim().isBlank() -> "El correo no puede estar vacío."
                    !Patterns.EMAIL_ADDRESS.matcher(nuevoCorreo).matches() -> "Formato de correo inválido."
                    else -> null
                }
            )
        }
    }

    fun actualizarContrasena(nuevaContrasena: String) {
        _uiState.update { it.copy(contrasena = nuevaContrasena) }
    }

    fun actualizarConfirmarContrasena(nuevaConfirmarContrasena: String) {
        _uiState.update { it.copy(confirmarContrasena = nuevaConfirmarContrasena) }
    }

    fun actualizarCorreoInicio(nuevoCorreo: String) {
        _uiState.update {it.copy(
            correoInicio = nuevoCorreo,
            correoErrorInicio = when {
                nuevoCorreo.trim().isBlank() -> "El correo no puede estar vacío."
                !Patterns.EMAIL_ADDRESS.matcher(nuevoCorreo).matches() -> "Formato de correo inválido."
                else -> null
            }
        ) }
    }

    fun actualizarContrasenaInicio(nuevaContrasena: String) {
        _uiState.update { it.copy(contrasenaInicio = nuevaContrasena) }
    }

    private val _creacionExitosa =
        MutableStateFlow(false) // => Almacena el valor y emite actualizaciones
    val creacionExitosa: StateFlow<Boolean> = _creacionExitosa.asStateFlow() // => Expone StateFlow publico para la UI

    private val _inicioExitoso = MutableStateFlow(false)
    val inicioExitoso: StateFlow<Boolean> = _inicioExitoso.asStateFlow()

}