package com.example.healthybotfront.data.source.remote.dto



data class ProgressDto(
    val progressId: Long?,
    val goalId: Long,
    val date: String,
    val completed: Boolean,
    val notes: String? = null
)