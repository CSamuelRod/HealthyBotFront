package com.example.healthybotfront.domain.usecase.progressUseCases

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.repository.ProgressRepository

/**
 * Caso de uso para guardar el progreso de un hábito.
 *
 * @property repository Repositorio que maneja las operaciones de progreso.
 */
class SaveProgressUseCase(
    private val repository: ProgressRepository
) {
    /**
     * Guarda el progreso proporcionado y retorna un [Result] con el progreso guardado o la excepción ocurrida.
     *
     * @param progressDto Objeto que contiene los datos del progreso a guardar.
     * @return [Result] que puede ser exitoso con el progreso guardado o fallido con la excepción.
     */
    suspend operator fun invoke(progressDto: ProgressDto): Result<ProgressDto> {
        return try {
            val response = repository.saveProgress(progressDto)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
