package com.example.healthybotfront.domain.usecase.habitUseCases

import com.example.healthybotfront.data.repository.HabitRepository
import com.example.healthybotfront.data.source.remote.dto.HabitDto

/**
 * Caso de uso para crear un nuevo hábito.
 *
 * @property repository Repositorio encargado de manejar los hábitos.
 */
class CreateHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Crea un nuevo hábito en el sistema.
     *
     * @param habit Objeto [HabitDto] con los datos del hábito a crear.
     * @return El hábito creado con los datos completos (incluido el ID generado).
     */
    suspend operator fun invoke(habit: HabitDto): HabitDto {
        return repository.createHabit(habit)
    }
}
