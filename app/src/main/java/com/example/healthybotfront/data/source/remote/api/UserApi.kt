package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.*
import retrofit2.http.*

interface UserApi {

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") userId: Long): UserDto

    @PUT("/api/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: Long,
        @Body userDto: UserDto
    ): UserDto

    @DELETE("/api/users/{id}")
    suspend fun deleteUser(@Path("id") userId: Long) : retrofit2.Response<Unit>

    @PUT("/api/users/reset-password")
    suspend fun resetPassword(@Body request: LoginRequest) : LoginRequest

}
