package com.example.healthybotfront.data.source.remote.dto

import java.time.LocalDate
import java.util.Date


data class ProgressDto(
    val goalId: Long,
    val date: String,
    val completed: Boolean,
    val notes: String? = null
)