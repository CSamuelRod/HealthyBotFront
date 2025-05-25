package com.example.healthybotfront.domain.usecase.AuthUseCases

import com.example.healthybotfront.data.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): String {
        return authRepository.register(name, lastname, email, password)
    }
}