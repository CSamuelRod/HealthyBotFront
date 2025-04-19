package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.AuthApi
import com.example.healthybotfront.data.source.remote.dto.LoginRequest
import com.example.healthybotfront.data.source.remote.dto.RegisterRequest

class AuthRepository(
    private val authApi: AuthApi
) {
    suspend fun register(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): String {
        return try {
            val response = authApi.register(RegisterRequest(name, lastname, email, password))
            response.message
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message ?: "Unknown error"}"
        }
    }

    suspend fun login(email: String, password: String): Pair<String, Long?> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            Pair(response.message, response.userId) // Retorna mensaje y el ID del usuario
        } catch (e: Exception) {
            e.printStackTrace()
            Pair("Error: ${e.message ?: "Unknown error"}", null)
        }
    }
}