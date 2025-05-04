package com.example.healthybotfront.data.source.remote.dto

import java.time.LocalDate

data class GoalDto(
    val user_id: Long,
    val habit_id: Long,
    val objective: String,
    val frequency: String, // Puedes usar enum si mapeas correctamente
    val startDate: String,
    val endDate: String
)
