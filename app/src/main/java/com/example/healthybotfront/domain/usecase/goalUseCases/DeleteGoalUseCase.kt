package com.example.healthybotfront.domain.usecase.goalUseCases

import com.example.healthybotfront.data.repository.GoalRepository

class DeleteGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(goalId: Long) = repository.deleteGoal(goalId)
}