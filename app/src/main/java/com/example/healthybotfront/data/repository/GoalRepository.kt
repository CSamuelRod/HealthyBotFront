package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.GoalApi
import com.example.healthybotfront.data.source.remote.dto.GoalDto

/**
 * Repositorio para manejar operaciones relacionadas con metas (goals).
 *
 * @property goalApi API remota para realizar llamadas de red relacionadas con goals.
 */
class GoalRepository(
    private val goalApi: GoalApi
) {

    /**
     * Obtiene la meta asociada a un hábito específico.
     *
     * @param habitId ID del hábito del cual obtener la meta.
     * @return [GoalDto] con la información de la meta.
     */
    suspend fun getGoalByHabitId(habitId: Long): GoalDto {
        return goalApi.getGoalByHabitId(habitId)
    }

    /**
     * Crea una nueva meta enviando los datos al servidor.
     *
     * @param goalDto Objeto que contiene los datos de la meta a crear.
     * @return [GoalDto] con la meta creada.
     */
    suspend fun createGoal(goalDto: GoalDto): GoalDto {
        return goalApi.createGoal(goalDto)
    }

    /**
     * Actualiza una meta existente con los datos proporcionados.
     *
     * @param goalId ID de la meta que se desea actualizar.
     * @param goalDto Objeto con los nuevos datos para la meta.
     * @return [GoalDto] con la meta actualizada.
     */
    suspend fun updateGoal(goalId: Long, goalDto: GoalDto): GoalDto {
        return goalApi.updateGoal(goalId, goalDto)
    }

    /**
     * Elimina una meta por su ID.
     *
     * @param goalId ID de la meta a eliminar.
     */
    suspend fun deleteGoal(goalId: Long) {
        goalApi.deleteGoal(goalId)
    }
}
