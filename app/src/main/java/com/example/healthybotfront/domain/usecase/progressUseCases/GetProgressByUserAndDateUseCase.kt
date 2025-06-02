package com.example.healthybotfront.domain.usecase.progressUseCases

import com.example.healthybotfront.data.repository.ProgressRepository
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import java.time.LocalDate

/**
 * Caso de uso para obtener el progreso de un usuario en una fecha específica.
 *
 * @property repository Repositorio que maneja las operaciones de progreso.
 */
class GetProgressByUserAndDateUseCase(
    private val repository: ProgressRepository
) {

    /**
     * Obtiene la lista de progresos del usuario en la fecha dada.
     *
     * @param userId ID del usuario.
     * @param date Fecha para la cual se desea obtener el progreso.
     * @return Lista de objetos ProgressDto con los progresos.
     */
    suspend operator fun invoke(userId: Long, date: LocalDate): List<ProgressDto> {
        return repository.getProgressByUserAndDate(userId, date)
    }
}
