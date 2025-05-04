package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.GoalApi
import com.example.healthybotfront.data.source.remote.dto.GoalDto

class GoalRepository(
    private val goalApi: GoalApi
) {
    suspend fun getGoalByHabitId(habitId: Long): GoalDto = goalApi.getGoalByHabitId(habitId)
    suspend fun createGoal(goalDto: GoalDto): GoalDto = goalApi.createGoal(goalDto)
    suspend fun updateGoal(goalId: Long, goalDto: GoalDto): GoalDto = goalApi.updateGoal(goalId, goalDto)
    suspend fun deleteGoal(goalId: Long) = goalApi.deleteGoal(goalId)
}
