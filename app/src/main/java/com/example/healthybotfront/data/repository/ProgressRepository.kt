package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.ProgressApi
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import java.time.LocalDate

class ProgressRepository(
    private val progressApi: ProgressApi
) {
    suspend fun saveProgress(progressDto: ProgressDto): ProgressDto {
        return progressApi.saveProgress(progressDto)
    }

    suspend fun getProgressPercentageByUserId(userId: Long): List<ProgressPercentageDto> {
        return progressApi.getProgressPercentageByUserId(userId)
    }

    suspend fun deleteProgress(progressId: Long) {
        progressApi.delete(progressId)
    }

    suspend fun getProgressByUserAndDate(userId: Long, date: LocalDate): List<ProgressDto> {
        return progressApi.getProgressByUserAndDate(userId, date)
    }

}
