package com.example.healthybotfront.data.source.remote.dto

data class GoalDto(
    val goalId: Long?, // 👈 ahora disponible
    val habit_id: Long,
    val objective: String,
    val frequency: String, // Puedes usar enum si mapeas correctamente
    val startDate: String,
    val endDate: String
)
