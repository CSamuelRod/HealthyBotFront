package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProgressApi {
        @POST("/progress")
        suspend fun saveProgress(@Body progress: ProgressDto):ProgressDto


        @GET("/progress/habit/{id}")
        suspend fun getProgressListByHabitId(@Path("id") id: Long):List<ProgressDto>

}