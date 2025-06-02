package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.*
import retrofit2.http.*

/**
 * Interfaz que define las llamadas API relacionadas con el manejo de usuarios.
 * Utiliza Retrofit para realizar peticiones HTTP.
 */
interface UserApi {

    /**
     * Obtiene un usuario por su ID.
     *
     * @param userId ID del usuario a obtener
     * @return DTO con los datos del usuario solicitado
     */
    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") userId: Long): UserDto

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param userId ID del usuario a actualizar
     * @param userDto DTO con los nuevos datos del usuario
     * @return DTO con los datos actualizados del usuario
     */
    @PUT("/api/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: Long,
        @Body userDto: UserDto
    ): UserDto

    /**
     * Elimina un usuario por su ID.
     *
     * @param userId ID del usuario a eliminar
     * @return Response vacío que indica el resultado de la operación
     */
    @DELETE("/api/users/{id}")
    suspend fun deleteUser(@Path("id") userId: Long) : retrofit2.Response<Unit>

    /**
     * Solicita el reseteo de contraseña para un usuario.
     *
     * @param request Objeto con la información necesaria para resetear la contraseña (por ejemplo email)
     * @return Objeto con la información de login, posiblemente con la contraseña reseteada
     */
    @PUT("/api/users/reset-password")
    suspend fun resetPassword(@Body request: LoginRequest) : LoginRequest

}
