package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.UserRepository

class DeleteUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteUser(id)
}