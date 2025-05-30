package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.HabitApi
import com.example.healthybotfront.data.source.remote.dto.HabitDto

class HabitRepository(
    private val habitApi: HabitApi
){

    suspend fun createHabit(habit: HabitDto): HabitDto {
        return habitApi.createHabit(habit.userId!!, habit)
    }

    suspend fun getHabitById(habitId: Long): HabitDto? {
        return habitApi.getHabitById(habitId)
    }
    suspend fun getHabitsByUserId(userId: Long): List<HabitDto> {
        return habitApi.getHabitsByUserId(userId)
    }

    suspend fun updateHabit(habitId: Long, habitDto: HabitDto): HabitDto {
        return habitApi.updateHabit(habitId, habitDto)
    }

    suspend fun deleteHabitById(habitId : Long) {
        val response = habitApi.delete(habitId)
        if (!response.isSuccessful) {
            throw Exception("Error en el servidor al eliminar hábito")
        }
    }

}