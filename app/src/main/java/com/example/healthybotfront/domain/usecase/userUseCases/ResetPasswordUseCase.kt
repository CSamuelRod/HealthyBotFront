package com.example.healthybotfront.domain.usecase.userUseCases

import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.dto.LoginRequest

class ResetPasswordUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, newPassword: String): LoginRequest {
        return repository.resetPassword(email, newPassword)
    }
}