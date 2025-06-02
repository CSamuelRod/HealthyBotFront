package com.example.healthybotfront.domain.usecase.userUseCases

import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.dto.UserDto

/**
 * Caso de uso para obtener un usuario por su ID.
 *
 * @property repository Repositorio que maneja las operaciones relacionadas con el usuario.
 */
class GetUserUseCase(
    private val repository: UserRepository
) {
    /**
     * Obtiene un usuario dado su ID.
     *
     * @param id ID del usuario a obtener.
     * @return UserDto con la información del usuario.
     */
    suspend operator fun invoke(id: Long): UserDto = repository.getUser(id)
}
