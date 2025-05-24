package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.time.LocalDate

interface ProgressApi {
        @POST("/progress")
        suspend fun saveProgress(@Body progress: ProgressDto):ProgressDto

        @GET("/progress/habit/{userId}/progress-percentage")
        suspend fun getProgressPercentageByUserId(@Path("userId") userId: Long): List<ProgressPercentageDto>

        @DELETE("/progress/{id}")
        suspend fun delete(@Path("id") id: Long)

        @GET("/progress/user/{userId}/date/{date}")
        suspend fun getProgressByUserAndDate(
                @Path("userId") userId: Long,
                @Path("date") date: LocalDate // formato dd-MM-yyyy
        ): List<ProgressDto>

}