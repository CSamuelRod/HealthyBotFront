package com.example.healthybotfront.data.source.remote.dto

import java.time.LocalDate

data class UserDto(
    val id: Long,
    val name: String,
    val lastName: String,
    val email: String,
    val registrationDate: String?
)
