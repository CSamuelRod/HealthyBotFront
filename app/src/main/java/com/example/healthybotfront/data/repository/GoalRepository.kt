package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.GoalApi
import com.example.healthybotfront.data.source.remote.dto.GoalDto

class GoalRepository(
    private val api: GoalApi
) {
    suspend fun getGoalByHabitId(habitId: Long): GoalDto = api.getGoalByHabitId(habitId)
    suspend fun createGoal(goalDto: GoalDto): GoalDto = api.createGoal(goalDto)
    suspend fun updateGoal(goalId: Long, goalDto: GoalDto): GoalDto = api.updateGoal(goalId, goalDto)
    suspend fun deleteGoal(goalId: Long) = api.deleteGoal(goalId)
}
