package com.example.healthybotfront.data.source.remote.dto


data class HabitDto(
    val habitId: Long? = null,
    val name: String,
    val description: String,
    val goalId:Long? = null,
    val userId: Long? = null
)
