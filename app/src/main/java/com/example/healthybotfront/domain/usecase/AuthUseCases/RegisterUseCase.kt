package com.example.healthybotfront.domain.usecase.AuthUseCases

import com.example.healthybotfront.data.repository.AuthRepository

/**
 * Caso de uso para registrar un nuevo usuario.
 *
 * Recibe los datos necesarios para registrar al usuario y delega la llamada al repositorio.
 *
 * @property authRepository Repositorio encargado de manejar la lógica de autenticación.
 */
class RegisterUseCase(
    private val authRepository: AuthRepository
) {

    /**
     * Ejecuta el registro de un usuario nuevo.
     *
     * @param name Nombre del usuario.
     * @param lastname Apellido del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña para el usuario.
     * @return Mensaje de resultado de la operación, que puede indicar éxito o error.
     */
    suspend operator fun invoke(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): String {
        return authRepository.register(name, lastname, email, password)
    }
}
