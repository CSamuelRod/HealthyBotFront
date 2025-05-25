package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.HabitDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HabitApi {

    @POST("/api/habits/{userId}")
    suspend fun createHabit(
        @Path("userId") userId: Long,
        @Body habitDto: HabitDto
    ): HabitDto

    @GET("/api/habits/{habitId}")
    suspend fun getHabitById(@Path("habitId") id: Long): HabitDto

    @GET("/api/habits/user/{userId}")
    suspend fun getHabitsByUserId(@Path("userId") userId: Long): List<HabitDto>

    @PUT("/api/habits/{habitId}")
    suspend fun updateHabit(
        @Path("habitId") habitId: Long,
        @Body habitDto: HabitDto
    ): HabitDto


    @DELETE("/api/habits/{habitId}")
    suspend fun delete(@Path("habitId") habitId: Long)
}