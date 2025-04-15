package com.example.healthybotfront.data.source.remote.dto

data class RegisterRequest(
    val name: String,
    val lastname: String,
    val email: String,
    val password: String
)