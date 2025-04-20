package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.UserApi
import com.example.healthybotfront.data.source.remote.dto.UserDto

class UserRepository(
    private val api: UserApi
) {

    suspend fun getUser(userId: Long): UserDto = api.getUserById(userId)

    suspend fun updateUser(userId: Long, userDto: UserDto): UserDto =
        api.updateUser(userId, userDto)

    suspend fun deleteUser(userId: Long) = api.deleteUser(userId)
}
