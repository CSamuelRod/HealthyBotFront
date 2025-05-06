package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.source.remote.api.ProgressApi

class SaveProgressUseCase(
    private val api: ProgressApi
) {
    suspend operator fun invoke(progress: ProgressDto): ProgressDto {
        return api.saveProgress(progress)
    }
}
