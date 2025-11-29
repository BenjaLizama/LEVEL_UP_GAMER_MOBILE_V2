package com.levelup.levelupgamer.viewmodel.autenticacion

import android.text.format.DateFormat
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import com.levelup.levelupgamer.db.entidades.Usuario
import com.levelup.levelupgamer.db.repository.UsuarioRepository
import com.levelup.levelupgamer.model.usuarios.CrearUsuarioDTO
import com.levelup.levelupgamer.repository.usuarios.PostUsuarios
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale

@HiltViewModel
class AutenticacionViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val preferenciasRepository: PreferenciasUsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EstadoFormularioUI())
    val uiState: StateFlow<EstadoFormularioUI> = _uiState.asStateFlow()

    // Repositorio de usuarios
    private val postUsuariosRepository = PostUsuarios()

    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun crearCuenta() {
        viewModelScope.launch {
            try {
                val estado = uiState.value

                // 1. Asignar el resultado de parse() directamente y usar !!
                // Esto le dice al compilador: "Estoy seguro de que esto no es null".
                // Si el parseo falla (ParseException), el catch lo atrapa.
                // Si devuelve null (depende de la implementación, pero es raro), lanza NPE que el catch también atrapa.
                val fechaDate = formatter.parse("2000-08-08")!!

                val usuarioCreado = CrearUsuarioDTO(
                    correo = estado.correo,
                    contrasena = estado.contrasena,
                    nombre = estado.nombre,
                    apellido = estado.apellido,
                    fechaNacimiento = fechaDate, // Asignación de Date no nula
                    nombreUsuario = estado.nombre + estado.apellido
                )

                val usuarioRetorno = postUsuariosRepository.crearUsuario(usuarioCreado)

                if (usuarioRetorno == null) {
                    // Es mejor lanzar una excepción para ser capturada en lugar de un Error
                    throw Exception("Error al crear el usuario en el servidor.")
                }

                print("Usuario ${usuarioRetorno.nombre} creado con exito!")

            } catch (e: Exception) {
                _uiState.update { it.copy(mensajeError = "Error al crear la cuenta. Detalles: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun iniciarSesion() {
        viewModelScope.launch {
            try {

                val estado = _uiState.value
                _uiState.update { it.copy(isLoading = true) }

                if (!validarFormularioInicio()) {
                    _uiState.update { it.copy(
                        mensajeError = "Por favor, corrige los errores en el formulario."
                    ) }
                    return@launch
                }

                val usuarioValidado = usuarioRepository.validarCredenciales(estado.correoInicio, estado.contrasenaInicio)

                if (usuarioValidado == null) {
                    _uiState.update { it.copy(
                        mensajeError = "La contraseña o el correo son incorrectos."
                    ) }
                    return@launch
                }
                preferenciasRepository.guardarIdUsuario(usuarioValidado.idUsuario)
                preferenciasRepository.guardarNombreUsuario(usuarioValidado.nombre)
                preferenciasRepository.guardarApellidoUsuario(usuarioValidado.apellido)
                preferenciasRepository.guardarCorreoUsuario(usuarioValidado.correo)
                preferenciasRepository.guardarEstadoLogueado(true)



                delay(2500L)
                _inicioExitoso.value = true

            } catch (e: kotlin.Exception) {
                _uiState.update { it.copy(mensajeError = "Error al iniciar sesion.") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
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