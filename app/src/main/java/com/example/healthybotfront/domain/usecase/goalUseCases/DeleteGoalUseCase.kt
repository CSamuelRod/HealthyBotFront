package com.example.healthybotfront.domain.usecase.goalUseCases

import com.example.healthybotfront.data.repository.GoalRepository

/**
 * Caso de uso para eliminar un objetivo (goal) por su ID.
 *
 * @property repository Repositorio encargado de manejar los objetivos.
 */
class DeleteGoalUseCase(
    private val repository: GoalRepository
) {

    /**
     * Ejecuta la eliminación del objetivo con el ID especificado.
     *
     * @param goalId ID del objetivo a eliminar.
     */
    suspend operator fun invoke(goalId: Long) {
        repository.deleteGoal(goalId)
    }
}
