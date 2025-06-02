package com.example.healthybotfront.domain.usecase.userUseCases

import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.dto.UserDto

/**
 * Caso de uso para actualizar la información de un usuario.
 *
 * @property repository Repositorio que maneja las operaciones relacionadas con el usuario.
 */
class UpdateUserUseCase(
    private val repository: UserRepository
) {
    /**
     * Actualiza los datos de un usuario específico.
     *
     * @param userId ID del usuario a actualizar.
     * @param updated Objeto [UserDto] con la información actualizada.
     * @return El usuario actualizado como [UserDto].
     */
    suspend fun updateUser(userId: Long, updated: UserDto): UserDto {
        return repository.updateUser(userId, updated)
    }
}
