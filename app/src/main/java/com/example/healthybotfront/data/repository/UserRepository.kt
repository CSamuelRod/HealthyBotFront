package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.UserApi
import com.example.healthybotfront.data.source.remote.dto.UserDto

class UserRepository(
    private val userApi: UserApi
) {

    suspend fun getUser(userId: Long): UserDto = userApi.getUserById(userId)

    suspend fun updateUser(userId: Long, userDto: UserDto): UserDto =
        userApi.updateUser(userId, userDto)

    suspend fun deleteUser(userId: Long) = userApi.deleteUser(userId)
}
