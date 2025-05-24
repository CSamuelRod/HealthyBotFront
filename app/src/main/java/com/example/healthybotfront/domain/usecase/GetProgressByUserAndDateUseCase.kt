package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import java.time.LocalDate

class GetProgressByUserAndDateUseCase(
    private val repository: ProgressRepository
) {

    suspend fun invoke(userId: Long, date: LocalDate): List<ProgressDto> {
        return repository.getProgressByUserAndDate(userId, date)
    }

}