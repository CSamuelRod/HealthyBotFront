package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

/**
 * Caso de uso para obtener un hábito por su ID.
 *
 * @property repository Repositorio encargado de manejar los hábitos.
 */
class GetHabitByIdUseCase(
    private val repository: HabitRepository
) {
    /**
     * Obtiene un hábito según su ID.
     *
     * @param habitId ID del hábito a obtener.
     * @return [HabitDto] si se encuentra, o null si no existe.
     */
    suspend operator fun invoke(habitId: Long): HabitDto? {
        return repository.getHabitById(habitId)
    }
}
