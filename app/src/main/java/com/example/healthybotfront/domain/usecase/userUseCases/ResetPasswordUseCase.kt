package com.example.healthybotfront.domain.usecase.userUseCases

import com.example.healthybotfront.data.repository.UserRepository
import com.example.healthybotfront.data.source.remote.dto.LoginRequest

/**
 * Caso de uso para resetear la contraseña de un usuario.
 *
 * @property repository Repositorio que maneja las operaciones relacionadas con el usuario.
 */
class ResetPasswordUseCase(
    private val repository: UserRepository
) {
    /**
     * Resetea la contraseña del usuario con el email proporcionado.
     *
     * @param email Correo electrónico del usuario.
     * @param newPassword Nueva contraseña que se desea establecer.
     * @return LoginRequest con la nueva información de login.
     */
    suspend operator fun invoke(email: String, newPassword: String): LoginRequest {
        return repository.resetPassword(email, newPassword)
    }
}
