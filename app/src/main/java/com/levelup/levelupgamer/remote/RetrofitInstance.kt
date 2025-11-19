package com.levelup.levelupgamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ProductoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8082/api/productos")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApiService::class.java)
    }
}