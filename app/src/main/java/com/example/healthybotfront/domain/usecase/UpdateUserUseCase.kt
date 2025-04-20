package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.dto.UserDto

class UpdateUserUseCase(
    private val repository: UserRepository
) {
    // En el UseCase
    suspend fun updateUser(userId: Long, updated: UserDto): UserDto {
        // Aquí llamas al Repository que va a hacer el update en la base de datos
        return repository.updateUser(userId, updated)
    }
}
