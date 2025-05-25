package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

class UpdateHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long, name: String, description: String): HabitDto {
        val habitDto = HabitDto(
            habitId = habitId,
            name = name,
            description = description,
            goalId = null,
            userId = null
        )
        return repository.updateHabit(habitId, habitDto)
    }
}
