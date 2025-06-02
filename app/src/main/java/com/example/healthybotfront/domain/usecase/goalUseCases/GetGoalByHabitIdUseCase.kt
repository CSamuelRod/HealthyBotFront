package com.example.healthybotfront.domain.usecase.goalUseCases

import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.source.remote.dto.GoalDto

/**
 * Caso de uso para obtener un objetivo (goal) asociado a un hábito específico.
 *
 * @property repository Repositorio encargado de manejar los objetivos.
 */
class GetGoalByHabitIdUseCase(
    private val repository: GoalRepository
) {

    /**
     * Obtiene el objetivo relacionado con el hábito indicado por su ID.
     *
     * @param habitId ID del hábito cuyo objetivo se desea obtener.
     * @return El [GoalDto] correspondiente al hábito.
     */
    suspend operator fun invoke(habitId: Long): GoalDto {
        return repository.getGoalByHabitId(habitId)
    }
}
