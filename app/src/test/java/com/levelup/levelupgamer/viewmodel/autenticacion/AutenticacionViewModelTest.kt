package com.levelup.levelupgamer.viewmodel.autenticacion

import com.levelup.levelupgamer.TestDispatcherRule // Tu regla custom
import com.levelup.levelupgamer.data.PreferenciasUsuarioRepository
import com.levelup.levelupgamer.model.usuarios.UsuarioResponseDTO
import com.levelup.levelupgamer.model.usuarios.UsuarioLogeadoDTO
import com.levelup.levelupgamer.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.After
// NOTA: Las importaciones de Android.util.Patterns y java.util.regex se eliminan de aquí.


@ExperimentalCoroutinesApi
class AutenticacionViewModelTest {

    @get:Rule
    val coroutineRule = TestDispatcherRule()

    private val mockPreferenciasRepository: PreferenciasUsuarioRepository = mockk(relaxed = true)
    private val mockUserRepository: UserRepository = mockk(relaxed = true)

    private lateinit var viewModel: AutenticacionViewModel

    // El bloque init y el After se eliminan ya que la dependencia de Patterns se ha resuelto.

    @Before
    fun setup() {
        viewModel = AutenticacionViewModel(
            preferenciasRepository = mockPreferenciasRepository,
            userRepository = mockUserRepository
        )
        // Inicialización de estado base VÁLIDO.
        viewModel.actualizarNombre("Test")
        viewModel.actualizarApellido("User")
        viewModel.actualizarContrasena("password123")
        viewModel.actualizarConfirmarContrasena("password123")

        viewModel.actualizarCorreoInicio("test@email.com") // Debe ser válido
        viewModel.actualizarContrasenaInicio("password123")
    }

    // --- TESTEO DE LA LÓGICA DE NEGOCIO (CREAR CUENTA) ---

    @Test
    fun crearCuenta_exitoEnRed_debeGuardarPreferenciasYSetearCreacionExitosa() = runTest {
        // GIVEN: Mock de éxito de red con datos completos
        val usuarioDTO = UsuarioResponseDTO(
            idUsuario = 10L, nombre = "Test", apellido = "User",
            correo = "test@email.com", imagenPerfilURL = "url_perfil.png",
            contrasena = "hashed_pass", fechaNacimiento = "2000-05-05",
            nombreUsuario = "TestUser", stripeCostumerId = "cus_abc"
        )
        coEvery { mockUserRepository.agregarUsuario(any()) } returns usuarioDTO

        // 🚨 Configurar correo válido para pasar la validación (usando EmailValidator)
        viewModel.actualizarCorreo("valid@email.com")

        // WHEN
        viewModel.crearCuenta()
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(viewModel.creacionExitosa.value)
        assertNull(viewModel.uiState.value.mensajeError)

        coVerify(exactly = 1) {
            mockPreferenciasRepository.guardarIdUsuario(usuarioDTO.idUsuario)
            mockPreferenciasRepository.guardarNombreUsuario(usuarioDTO.nombre)
            mockPreferenciasRepository.guardarApellidoUsuario(usuarioDTO.apellido)
            mockPreferenciasRepository.guardarCorreoUsuario(usuarioDTO.correo)
            mockPreferenciasRepository.guardarImagenPerfil(usuarioDTO.imagenPerfilURL)
            mockPreferenciasRepository.guardarEstadoLogueado(true)
        }
    }

    @Test
    fun crearCuenta_fallaPorConflicto_debeMostrarMensajeDeError() = runTest {
        coEvery { mockUserRepository.agregarUsuario(any()) } returns null
        viewModel.actualizarCorreo("conflict@email.com") // Necesario para pasar validación.

        // WHEN
        viewModel.crearCuenta()
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.creacionExitosa.value)
        assertEquals("Conflicto al crear el usuario, el correo ya se encuentra registrado.",
            viewModel.uiState.value.mensajeError)
    }

    // --- TESTEO DE LA LÓGICA DE NEGOCIO (INICIAR SESIÓN) ---

    @Test
    fun iniciarSesion_exitoEnRed_debeGuardarPreferenciasYSetearInicioExitoso() = runTest {
        // GIVEN
        val loginDTO = UsuarioLogeadoDTO(
            idUsuario = 5L, nombre = "Test", apellido = "User",
            correo = "test@email.com", token = "jwt_token", imagenPerfilURL = "url_perfil_login.png",
            nombreUsuario = "TestUserLogin"
        )
        coEvery { mockUserRepository.iniciarSesion(any()) } returns loginDTO
        viewModel.actualizarCorreoInicio("valid@email.com") // Necesario para pasar validación.

        // WHEN
        viewModel.iniciarSesion()
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(viewModel.inicioExitoso.value)
        assertNull(viewModel.uiState.value.mensajeError)

        coVerify(exactly = 1) {
            mockPreferenciasRepository.guardarIdUsuario(loginDTO.idUsuario)
            mockPreferenciasRepository.guardarNombreUsuario(loginDTO.nombre)
            mockPreferenciasRepository.guardarApellidoUsuario(loginDTO.apellido)
            mockPreferenciasRepository.guardarCorreoUsuario(loginDTO.correo)
            mockPreferenciasRepository.guardarImagenPerfil(loginDTO.imagenPerfilURL)
            mockPreferenciasRepository.guardarEstadoLogueado(true)
        }
    }

    @Test
    fun iniciarSesion_credencialesInvalidas_debeMostrarMensajeDeError() = runTest {
        coEvery { mockUserRepository.iniciarSesion(any()) } returns null
        viewModel.actualizarCorreoInicio("valid@email.com") // Necesario para pasar validación.

        // WHEN
        viewModel.iniciarSesion()
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.inicioExitoso.value)
        assertEquals("Correo o contraseña incorrectos", viewModel.uiState.value.mensajeError)
    }

    // --- TESTEO DE VALIDACIONES SIMPLES Y DE CORREO ---

    @Test
    fun validarFormulario_contrasenasDiferentes_debeFallar() {
        viewModel.actualizarContrasena("password123")
        viewModel.actualizarConfirmarContrasena("password456")

        val esValido = viewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals("Las contraseñas no coinciden.", viewModel.uiState.value.confirmarContrasenaError)
    }

    @Test
    fun validarFormularioInicio_contrasenaCorta_debeFallar() {
        viewModel.actualizarContrasenaInicio("123")

        val esValido = viewModel.validarFormularioInicio()

        assertFalse(esValido)
        assertEquals("Mínimo 6 caracteres.", viewModel.uiState.value.contrasenaErrorInicio)
    }

    @Test
    fun actualizarNombre_nombreCorto_debeMostrarError() {
        viewModel.actualizarNombre("ab")

        assertEquals("Mínimo 3 caracteres.", viewModel.uiState.value.nombreError)
    }

    @Test
    fun actualizarCorreo_formatoInvalido_debeMostrarError() {
        // GIVEN: El validador puro de Kotlin (EmailValidator) se encargará de esto.
        // Si no creaste el EmailValidator, este test fallará porque 'correo@invalido'
        // no cumple el patrón REGEX y el ViewModel establecerá el error.

        // WHEN
        // 'correo@invalido' es un formato incorrecto según el REGEX estándar.
        viewModel.actualizarCorreo("correo@invalido")

        // THEN
        // El VM debe capturar que el EmailValidator devolvió false.
        assertEquals("Formato de correo inválido.", viewModel.uiState.value.correoError)
    }
}