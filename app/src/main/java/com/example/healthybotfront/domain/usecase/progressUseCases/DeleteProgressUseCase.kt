package com.example.healthybotfront.domain.usecase.progressUseCases

import com.example.healthybotfront.data.repository.ProgressRepository

class DeleteProgressUseCase (
    private val repository: ProgressRepository
) {
    suspend operator fun invoke(id:Long){
        return repository.deleteProgress(id)
    }
}