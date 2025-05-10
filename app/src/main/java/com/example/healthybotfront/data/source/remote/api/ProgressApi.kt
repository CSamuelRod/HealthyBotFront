package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ProgressApi {
        @POST("/progress")
        suspend fun saveProgress(@Body progress: ProgressDto):ProgressDto

}