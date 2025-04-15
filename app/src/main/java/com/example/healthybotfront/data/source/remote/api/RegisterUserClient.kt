package com.example.healthybotfront.data.source.remote.api


import com.example.healthybotfront.data.source.remote.dto.RegisterRequest
import com.example.healthybotfront.data.source.remote.dto.RegisterResponse
import retrofit2.http.*

// Interfaz de API
interface RegisterUserClient {


    @POST("/api/register")
    suspend fun register(@Body registerRequest: RegisterRequest) : RegisterResponse

}