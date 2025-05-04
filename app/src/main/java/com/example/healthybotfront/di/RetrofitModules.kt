package com.example.healthybotfront.di

import com.example.healthybotfront.data.source.remote.api.AuthApi
import com.example.healthybotfront.data.source.remote.api.GoalApi
import com.example.healthybotfront.data.source.remote.api.HabitApi
import com.example.healthybotfront.data.source.remote.api.UserApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    single<AuthApi> { get<Retrofit>().create(AuthApi::class.java) }
    single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
    single<HabitApi> { get<Retrofit>().create(HabitApi::class.java) }
    single<GoalApi> { get<Retrofit>().create(GoalApi::class.java) }
}