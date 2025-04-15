package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.RegisterRestRepository

class RegisterUseCase(
    private val registerRestRepository: RegisterRestRepository
) {

    suspend operator fun invoke(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): String {
        return registerRestRepository.register(name, lastname, email, password)
    }
}