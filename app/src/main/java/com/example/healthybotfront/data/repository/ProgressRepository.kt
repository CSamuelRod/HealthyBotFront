package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.ProgressApi
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import java.time.LocalDate

/**
 * Repositorio para manejar las operaciones relacionadas con el progreso del usuario.
 *
 * @property progressApi API remota para realizar llamadas relacionadas con el progreso.
 */
class ProgressRepository(
    private val progressApi: ProgressApi
) {

    /**
     * Guarda un registro de progreso.
     *
     * @param progressDto Datos del progreso a guardar.
     * @return [ProgressDto] con el progreso guardado.
     */
    suspend fun saveProgress(progressDto: ProgressDto): ProgressDto {
        return progressApi.saveProgress(progressDto)
    }

    /**
     * Obtiene el porcentaje de progreso por usuario.
     *
     * @param userId ID del usuario.
     * @return Lista de porcentajes de progreso por hábito.
     */
    suspend fun getProgressPercentageByUserId(userId: Long): List<ProgressPercentageDto> {
        return progressApi.getProgressPercentageByUserId(userId)
    }

    /**
     * Elimina un registro de progreso por su ID.
     *
     * @param progressId ID del progreso a eliminar.
     */
    suspend fun deleteProgress(progressId: Long) {
        progressApi.delete(progressId)
    }

    /**
     * Obtiene los registros de progreso de un usuario en una fecha específica.
     *
     * @param userId ID del usuario.
     * @param date Fecha para filtrar el progreso.
     * @return Lista de registros de progreso.
     */
    suspend fun getProgressByUserAndDate(userId: Long, date: LocalDate): List<ProgressDto> {
        return progressApi.getProgressByUserAndDate(userId, date)
    }
}
