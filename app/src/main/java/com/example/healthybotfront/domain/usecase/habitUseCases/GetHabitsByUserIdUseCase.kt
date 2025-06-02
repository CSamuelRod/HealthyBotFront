package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

/**
 * Caso de uso para obtener la lista de hábitos de un usuario específico.
 *
 * @property repository Repositorio para manejar operaciones relacionadas con hábitos.
 */
class GetHabitsByUserIdUseCase(
    private val repository: HabitRepository
) {
    /**
     * Obtiene la lista de hábitos asociados a un usuario dado.
     *
     * @param userId ID del usuario cuyos hábitos se desean obtener.
     * @return Lista de [HabitDto] con los hábitos del usuario.
     */
    suspend operator fun invoke(userId: Long): List<HabitDto> {
        return repository.getHabitsByUserId(userId)
    }
}
