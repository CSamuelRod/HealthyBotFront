package com.example.healthybotfront.domain.usecase.userUseCases

import com.example.healthybotfront.data.repository.UserRepository

/**
 * Caso de uso para eliminar un usuario por su ID.
 *
 * @property repository Repositorio que maneja las operaciones relacionadas con el usuario.
 */
class DeleteUserUseCase(
    private val repository: UserRepository
) {
    /**
     * Elimina el usuario con el ID especificado.
     *
     * @param id ID del usuario a eliminar.
     */
    suspend operator fun invoke(id: Long) = repository.deleteUser(id)
}
