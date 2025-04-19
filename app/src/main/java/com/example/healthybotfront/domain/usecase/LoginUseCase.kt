package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Pair<String, Long?> {
        return repository.login(email, password) // Retorna el mensaje y el userId
    }
}