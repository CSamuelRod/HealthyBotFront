package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.source.remote.dto.ProgressDto

class GetProgressByHabitIdUseCase(
    private val progressRepository: ProgressRepository
) {

    suspend operator fun invoke(habitId: Long): List<ProgressDto> {
        return progressRepository.getProgressListByHabitId(habitId)
    }
}