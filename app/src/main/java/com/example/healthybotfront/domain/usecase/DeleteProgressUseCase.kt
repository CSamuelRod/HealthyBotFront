package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.ProgressRepository

class DeleteProgressUseCase (
    private val repository: ProgressRepository
) {
    suspend operator fun invoke(id:Long){
        return repository.deleteProgress(id)
    }
}