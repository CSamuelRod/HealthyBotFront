package com.example.healthybotfront.domain.usecase.goalUseCases

import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.source.remote.dto.GoalDto

/**
 * Caso de uso para actualizar un objetivo (goal) existente.
 *
 * @property repository Repositorio encargado de manejar los objetivos.
 */
class UpdateGoalUseCase(
    private val repository: GoalRepository
) {
    /**
     * Actualiza un objetivo con la información proporcionada.
     *
     * @param goalId ID del objetivo que se desea actualizar.
     * @param goalDto Datos actualizados del objetivo.
     * @return El [GoalDto] actualizado.
     */
    suspend operator fun invoke(goalId: Long, goalDto: GoalDto): GoalDto =
        repository.updateGoal(goalId, goalDto)
}
