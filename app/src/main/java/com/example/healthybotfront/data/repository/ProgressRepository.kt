package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.ProgressApi
import com.example.healthybotfront.data.source.remote.dto.ProgressDto

class ProgressRepository(
    private val progressApi: ProgressApi
) {
    suspend fun saveProgress(progressDto: ProgressDto) : ProgressDto{
        return progressApi.saveProgress(progressDto)
    }
}