package com.example.healthybotfront.data.source.remote.dto


data class ProgressDto(
    val goalId: Long,
    val date: String,
    val completed: Boolean,
    val notes: String? = null
)