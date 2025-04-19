package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.LoginRequest
import com.example.healthybotfront.data.source.remote.dto.LoginResponse
import com.example.healthybotfront.data.source.remote.dto.RegisterRequest
import com.example.healthybotfront.data.source.remote.dto.RegisterResponse
import retrofit2.http.*

interface AuthApi {

    @POST("/api/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
