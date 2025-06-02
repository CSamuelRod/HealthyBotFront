package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.UserApi
import com.example.healthybotfront.data.source.remote.dto.LoginRequest
import com.example.healthybotfront.data.source.remote.dto.UserDto

/**
 * Repositorio para manejar operaciones relacionadas con el usuario.
 *
 * @property userApi API remota para realizar llamadas relacionadas con el usuario.
 */
class UserRepository(
    private val userApi: UserApi
) {

    /**
     * Obtiene un usuario por su ID.
     *
     * @param userId ID del usuario a obtener.
     * @return [UserDto] con los datos del usuario.
     */
    suspend fun getUser(userId: Long): UserDto {
        return userApi.getUserById(userId)
    }

    /**
     * Actualiza un usuario.
     *
     * @param userId ID del usuario a actualizar.
     * @param userDto Datos actualizados del usuario.
     * @return [UserDto] con los datos actualizados.
     */
    suspend fun updateUser(userId: Long, userDto: UserDto): UserDto {
        return userApi.updateUser(userId, userDto)
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param userId ID del usuario a eliminar.
     * @throws Exception si la eliminación falla.
     */
    suspend fun deleteUser(userId: Long) {
        val response = userApi.deleteUser(userId)
        if (!response.isSuccessful) {
            throw Exception("Error al eliminar el usuario")
        }
    }

    /**
     * Resetea la contraseña de un usuario.
     *
     * @param email Correo electrónico del usuario.
     * @param newPassword Nueva contraseña a establecer.
     * @return [LoginRequest] con los datos usados para el reset.
     */
    suspend fun resetPassword(email: String, newPassword: String): LoginRequest {
        val request = LoginRequest(email, newPassword)
        return userApi.resetPassword(request)
    }
}
