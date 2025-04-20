package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

class GetHabitsByUserIdUseCase(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(userId: Long): List<HabitDto> {
        return habitRepository.getHabitsByUserId(userId)
    }
}
