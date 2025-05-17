package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.HabitApi
import com.example.healthybotfront.data.source.remote.dto.HabitDto

class HabitRepository(
    private val habitApi: HabitApi
){

    suspend fun createHabit(habit: HabitDto): HabitDto {
        return habitApi.createHabit(habit.userId, habit)
    }

    suspend fun getHabitsByUserId(userId: Long): List<HabitDto> {
        return habitApi.getHabitsByUserId(userId)
    }

    suspend fun deleteHabitById(habitId : Long){
        return habitApi.delete(habitId)
    }
}