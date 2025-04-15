package com.example.healthybotfront.di

import com.example.healthybotfront.data.source.remote.api.RegisterUserClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val retrofitModule = module {
    // API retrofit
    single {
        Retrofit.Builder()
            // Se configura la URL del servicio REST
            .baseUrl("http://10.0.2.2:8080")
            // Se configura la serialización con JSON
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<RegisterUserClient> { get<Retrofit>().create(RegisterUserClient::class.java) }
}