package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.HabitRepository

class DeleteHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long) {
        repository.deleteHabitById(habitId)
    }
}