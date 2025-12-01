package com.levelup.levelupgamer

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.levelup.levelupgamer.remote.CarritoServiceApi
import com.levelup.levelupgamer.remote.ProductServiceApi
import com.levelup.levelupgamer.remote.UserServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL_USUARIOS = "http://54.85.135.89:8084/"
    private const val BASE_URL_PRODUCTOS = "http://54.85.135.89:8082/"

    private const val BASE_URL_CARRITO = "http://54.85.135.89:8083/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton // Provee la config del serializador
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_USUARIOS)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserServiceApi(retrofit: Retrofit): UserServiceApi {
        return retrofit.create(UserServiceApi::class.java)
    }

    @Provides
    @Singleton
    @Named("RetrofitProductos")
    fun provideRetrofitProductos(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL_PRODUCTOS)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideProductServiceApi(@Named("RetrofitProductos") retrofit: Retrofit): ProductServiceApi {

        return retrofit.create(ProductServiceApi::class.java)
    }

    @Provides
    @Singleton
    @Named("RetrofitCarrito")
    fun provideRetrofitCarrito(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL_CARRITO).addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient).build()

    }

    @Provides
    @Singleton
    fun provideCarritoServiceApi(
        @Named("RetrofitCarrito") retrofit: Retrofit
    ): CarritoServiceApi {
        return retrofit.create(CarritoServiceApi::class.java)
    }
}