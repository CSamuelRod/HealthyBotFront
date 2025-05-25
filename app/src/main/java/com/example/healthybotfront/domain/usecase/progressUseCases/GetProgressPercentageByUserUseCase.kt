package com.example.healthybotfront.domain.usecase.progressUseCases

import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto

class GetProgressPercentageByUserUseCase (
    private val repository: ProgressRepository
) {
    suspend operator fun invoke(userId: Long): List<ProgressPercentageDto> {
        return repository.getProgressPercentageByUserId(userId)
    }
}