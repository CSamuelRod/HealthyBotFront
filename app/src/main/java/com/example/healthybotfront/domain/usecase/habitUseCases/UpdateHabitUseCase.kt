package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

/**
 * Caso de uso para actualizar un hábito existente.
 *
 * @property repository Repositorio para manejar las operaciones relacionadas con hábitos.
 */
class UpdateHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Actualiza un hábito con los nuevos datos proporcionados.
     *
     * @param habitId ID del hábito a actualizar.
     * @param name Nuevo nombre para el hábito.
     * @param description Nueva descripción para el hábito.
     * @return [HabitDto] actualizado.
     */
    suspend operator fun invoke(habitId: Long, name: String, description: String): HabitDto {
        val habitDto = HabitDto(
            habitId = habitId,
            name = name,
            description = description,
            goalId = null,
            userId = null
        )
        return repository.updateHabit(habitId, habitDto)
    }
}
