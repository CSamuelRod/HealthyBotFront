package com.example.healthybotfront.data.source.remote.dto


data class HabitDto(
    val name: String,
    val description: String,
    val isCustom: Boolean = true,
    val userId: Long
)
