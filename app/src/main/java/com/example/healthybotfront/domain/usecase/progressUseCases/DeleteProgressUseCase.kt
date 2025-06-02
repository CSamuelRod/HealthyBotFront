package com.example.healthybotfront.domain.usecase.progressUseCases

import com.example.healthybotfront.data.repository.ProgressRepository

/**
 * Caso de uso para eliminar un progreso específico por su ID.
 *
 * @property repository Repositorio para manejar las operaciones de progreso.
 */
class DeleteProgressUseCase(
    private val repository: ProgressRepository
) {
    /**
     * Elimina el progreso identificado por el ID proporcionado.
     *
     * @param id ID del progreso a eliminar.
     */
    suspend operator fun invoke(id: Long) {
        repository.deleteProgress(id)
    }
}
