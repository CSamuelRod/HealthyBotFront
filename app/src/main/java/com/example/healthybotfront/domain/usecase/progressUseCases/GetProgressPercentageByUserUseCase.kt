package com.example.healthybotfront.domain.usecase.progressUseCases

import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto

/**
 * Caso de uso para obtener el porcentaje de progreso de los hábitos de un usuario.
 *
 * @property repository Repositorio que maneja las operaciones de progreso.
 */
class GetProgressPercentageByUserUseCase(
    private val repository: ProgressRepository
) {
    /**
     * Obtiene la lista de porcentajes de progreso para un usuario específico.
     *
     * @param userId ID del usuario.
     * @return Lista de ProgressPercentageDto con los porcentajes de progreso.
     */
    suspend operator fun invoke(userId: Long): List<ProgressPercentageDto> {
        return repository.getProgressPercentageByUserId(userId)
    }
}
