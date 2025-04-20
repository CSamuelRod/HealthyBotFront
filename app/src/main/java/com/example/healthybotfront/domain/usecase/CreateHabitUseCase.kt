package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

class CreateHabitUseCase(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habit: HabitDto): HabitDto {
        return habitRepository.createHabit(habit)
    }
}