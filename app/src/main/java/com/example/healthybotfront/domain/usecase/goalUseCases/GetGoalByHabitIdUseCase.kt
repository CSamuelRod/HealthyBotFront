package com.example.healthybotfront.domain.usecase.goalUseCases

import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.source.remote.dto.GoalDto

class GetGoalByHabitIdUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(habitId: Long): GoalDto = repository.getGoalByHabitId(habitId)
}