package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

class GetHabitByIdUseCase(
    private val repository: HabitRepository
) {

    suspend operator fun invoke(habitId: Long): HabitDto? {
        return repository.getHabitById(habitId)
    }
}
