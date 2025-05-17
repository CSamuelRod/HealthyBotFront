package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProgressApi {
        @POST("/progress")
        suspend fun saveProgress(@Body progress: ProgressDto):ProgressDto

        @GET("/progress/habit/{userId}/progress-percentage")
        suspend fun getProgressPercentageByUserId(@Path("userId") userId: Long): List<ProgressPercentageDto>

}