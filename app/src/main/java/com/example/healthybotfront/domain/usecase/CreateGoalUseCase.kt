package com.example.healthybotfront.domain.usecase

import com.example.healthybotfront.data.repository.GoalRepository;
import com.example.healthybotfront.data.source.remote.dto.GoalDto;

class CreateGoalUseCase(private val repository:GoalRepository) {
    suspend operator fun invoke(goalDto:GoalDto): GoalDto = repository.createGoal(goalDto)
}