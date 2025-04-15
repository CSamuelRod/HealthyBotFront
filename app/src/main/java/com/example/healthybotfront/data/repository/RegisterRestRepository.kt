package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.RegisterUserClient
import com.example.healthybotfront.data.source.remote.dto.RegisterRequest

class RegisterRestRepository(
    private val registerUserClient: RegisterUserClient
) {
    suspend fun register(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): String {
        return try {
            val response = registerUserClient.register(RegisterRequest(name, lastname, email, password))
            response.message
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message ?: "Unknown error"}"
        }
    }
}