package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository

/**
 * Caso de uso para eliminar un hábito por su ID.
 *
 * @property repository Repositorio encargado de manejar los hábitos.
 */
class DeleteHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Elimina un hábito identificado por su ID.
     *
     * @param habitId ID del hábito a eliminar.
     */
    suspend operator fun invoke(habitId: Long) {
        repository.deleteHabitById(habitId)
    }
}
