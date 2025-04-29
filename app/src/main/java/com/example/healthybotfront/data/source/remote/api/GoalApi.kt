package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.GoalDto
import retrofit2.http.*

interface GoalApi {

    @GET("/api/goals/{habitId}")
    suspend fun getGoalByHabitId(@Path("habitId") habitId: Long): GoalDto

    @POST("/api/goals/")
    suspend fun createGoal(@Body goalDto: GoalDto): GoalDto

    @PUT("/api/goals/{goalId}")
    suspend fun updateGoal(@Path("goalId") goalId: Long, @Body goalDto: GoalDto): GoalDto

    @DELETE("/api/goals/{goalId}")
    suspend fun deleteGoal(@Path("goalId") goalId: Long)
}
