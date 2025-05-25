package com.example.healthybotfront.domain.usecase.userUseCases

import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.dto.UserDto

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Long): UserDto = repository.getUser(id)
}