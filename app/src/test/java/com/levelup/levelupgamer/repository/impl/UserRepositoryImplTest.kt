package com.levelup.levelupgamer.repository.impl

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.levelup.levelupgamer.model.usuarios.AgregarUsuarioDTO
import com.levelup.levelupgamer.model.usuarios.IniciarSesionDTO
import com.levelup.levelupgamer.remote.UserServiceApi
import com.levelup.levelupgamer.repository.UserRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

fun createUserServiceApi(baseUrl: String): UserServiceApi {
    val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
    val contentType = "application/json".toMediaType()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    return retrofit.create(UserServiceApi::class.java)
}


class UserRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/").toString()
        val apiService = createUserServiceApi(baseUrl)
        userRepository = UserRepositoryImpl(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `agregarUsuario_debeRetornarUsuarioResponseDTO_en200OK`() = runTest {
        val successJson = """
            {
                "idUsuario": 101, 
                "correo": "test@mail.com",
                "contrasena": "hashed_pw", 
                "nombre": "Test", 
                "apellido": "User", 
                "fechaNacimiento": "2000-01-01", 
                "nombreUsuario": "TestUser", 
                "imagenPerfilURL": "url_perfil", 
                "stripeCostumerId": "cus_001"
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(successJson)
        )

        val dtoEntrada = AgregarUsuarioDTO(
            correo = "test@mail.com", contrasena = "pass", nombre = "T",
            apellido = "U", fechaNacimiento = "2000-01-01", nombreUsuario = "TU"
        )
        val resultado = userRepository.agregarUsuario(dtoEntrada)

        assertTrue(resultado != null)
        assertEquals(101L, resultado!!.idUsuario)
        assertEquals("TestUser", resultado.nombreUsuario)

        val request = mockWebServer.takeRequest()
        assertEquals("/api/usuarios", request.path)
    }

    @Test
    fun `agregarUsuario_debeRetornarNulo_enFallaDeServidor500`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
        )

        val dtoEntrada = AgregarUsuarioDTO(
            correo = "test@mail.com", contrasena = "pass", nombre = "T",
            apellido = "U", fechaNacimiento = "2000-01-01", nombreUsuario = "TU"
        )
        val resultado = userRepository.agregarUsuario(dtoEntrada)

        assertNull(resultado)
    }

    @Test
    fun `iniciarSesion_debeRetornarUsuarioLogeadoDTO_en200OK`() = runTest {

        val successJson = """
            {
                "idUsuario": 5, 
                "nombreUsuario": "TestUserLogin",
                "nombre": "Test", 
                "apellido": "User", 
                "correo": "login@test.com", 
                "imagenPerfilURL": "url_login", 
                "token": "JWT.VALID.TOKEN"
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(successJson)
        )

        val credenciales = IniciarSesionDTO(correo = "login@test.com", contrasena = "pass123")
        val resultado = userRepository.iniciarSesion(credenciales)

        assertTrue(resultado != null)
        assertEquals("JWT.VALID.TOKEN", resultado!!.token)
        assertEquals(5L, resultado.idUsuario)

        val request = mockWebServer.takeRequest()
        assertEquals("/api/usuarios/login", request.path) // Verifica el path según UserServiceApi
    }

    @Test
    fun `iniciarSesion_debeRetornarNulo_enRespuestaDeError4xx`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
        )

        val credenciales = IniciarSesionDTO(correo = "bad@test.com", contrasena = "wrong")
        val resultado = userRepository.iniciarSesion(credenciales)

        assertNull(resultado)
    }
}