package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.source.remote.dto.GoalDto

class UpdateGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(goalId: Long, goalDto: GoalDto): GoalDto =
        repository.updateGoal(goalId, goalDto)
}