package com.example.healthybotfront.domain.usecase.goalUseCases

import com.example.healthybotfront.data.repository.GoalRepository
import com.example.healthybotfront.data.source.remote.dto.GoalDto

/**
 * Caso de uso para crear un nuevo objetivo (goal).
 *
 * Recibe un objeto [GoalDto] y delega la creación al repositorio correspondiente.
 *
 * @property repository Repositorio encargado de manejar los objetivos.
 */
class CreateGoalUseCase(
    private val repository: GoalRepository
) {

    /**
     * Ejecuta la creación de un nuevo objetivo.
     *
     * @param goalDto Objeto que representa los datos del objetivo a crear.
     * @return El objetivo creado con sus datos actualizados.
     */
    suspend operator fun invoke(goalDto: GoalDto): GoalDto {
        return repository.createGoal(goalDto)
    }
}
