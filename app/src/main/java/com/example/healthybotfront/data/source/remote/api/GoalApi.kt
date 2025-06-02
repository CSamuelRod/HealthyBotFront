package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.GoalDto
import retrofit2.http.*

/**
 * Interfaz para las operaciones API relacionadas con metas (Goals).
 * Permite obtener, crear, actualizar y eliminar metas asociadas a hábitos.
 */
interface GoalApi {

    /**
     * Obtiene la meta asociada a un hábito específico por su ID.
     *
     * @param habitId ID del hábito para el cual se obtiene la meta
     * @return DTO con los detalles de la meta asociada al hábito
     */
    @GET("/api/goals/{habitId}")
    suspend fun getGoalByHabitId(@Path("habitId") habitId: Long): GoalDto

    /**
     * Crea una nueva meta.
     *
     * @param goalDto DTO con los datos de la meta a crear
     * @return DTO con la meta creada y sus detalles
     */
    @POST("/api/goals/")
    suspend fun createGoal(@Body goalDto: GoalDto): GoalDto

    /**
     * Actualiza una meta existente por su ID.
     *
     * @param goalId ID de la meta a actualizar
     * @param goalDto DTO con los nuevos datos de la meta
     * @return DTO con la meta actualizada
     */
    @PUT("/api/goals/{goalId}")
    suspend fun updateGoal(@Path("goalId") goalId: Long, @Body goalDto: GoalDto): GoalDto

    /**
     * Elimina una meta por su ID.
     *
     * @param goalId ID de la meta a eliminar
     */
    @DELETE("/api/goals/{goalId}")
    suspend fun deleteGoal(@Path("goalId") goalId: Long)
}
