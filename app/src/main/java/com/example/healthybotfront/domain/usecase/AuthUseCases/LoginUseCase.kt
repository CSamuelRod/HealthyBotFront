package com.example.healthybotfront.domain.usecase.AuthUseCases

import com.example.healthybotfront.data.repository.AuthRepository

/**
 * Caso de uso para manejar la lógica de inicio de sesión.
 *
 * @property repository Repositorio que maneja la autenticación.
 */
class LoginUseCase(
    private val repository: AuthRepository
) {
    /**
     * Ejecuta el proceso de login con el email y contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Par que contiene un mensaje (puede ser éxito o error) y el ID del usuario (o null si falla).
     */
    suspend operator fun invoke(email: String, password: String): Pair<String, Long?> {
        return repository.login(email, password) // Retorna el mensaje y el userId
    }
}
