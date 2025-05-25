package com.example.healthybotfront.domain.usecase.progressUseCases


import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.repository.ProgressRepository

class SaveProgressUseCase(
    private val progressRepository: ProgressRepository
) {
    suspend operator fun invoke(progressDto: ProgressDto): Result<ProgressDto> {
        return try {
            val response = progressRepository.saveProgress(progressDto)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
