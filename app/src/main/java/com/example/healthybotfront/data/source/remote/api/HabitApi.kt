package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.HabitDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Interfaz para las llamadas API relacionadas con los hábitos.
 * Proporciona métodos para crear, obtener, actualizar y eliminar hábitos.
 */
interface HabitApi {

    /**
     * Crea un nuevo hábito para un usuario específico.
     *
     * @param userId ID del usuario al que se le asociará el hábito
     * @param habitDto DTO con los datos del hábito a crear
     * @return DTO con el hábito creado y sus detalles
     */
    @POST("/api/habits/{userId}")
    suspend fun createHabit(
        @Path("userId") userId: Long,
        @Body habitDto: HabitDto
    ): HabitDto

    /**
     * Obtiene un hábito específico por su ID.
     *
     * @param id ID del hábito a obtener
     * @return DTO con los detalles del hábito solicitado
     */
    @GET("/api/habits/{habitId}")
    suspend fun getHabitById(@Path("habitId") id: Long): HabitDto

    /**
     * Obtiene la lista de hábitos asociados a un usuario.
     *
     * @param userId ID del usuario cuyos hábitos se desean obtener
     * @return Lista de DTOs con los hábitos del usuario
     */
    @GET("/api/habits/user/{userId}")
    suspend fun getHabitsByUserId(@Path("userId") userId: Long): List<HabitDto>

    /**
     * Actualiza un hábito existente por su ID.
     *
     * @param habitId ID del hábito a actualizar
     * @param habitDto DTO con los nuevos datos del hábito
     * @return DTO con el hábito actualizado
     */
    @PUT("/api/habits/{habitId}")
    suspend fun updateHabit(
        @Path("habitId") habitId: Long,
        @Body habitDto: HabitDto
    ): HabitDto

    /**
     * Elimina un hábito por su ID.
     *
     * @param habitId ID del hábito a eliminar
     * @return Respuesta Retrofit indicando el resultado de la operación
     */
    @DELETE("/api/habits/{habitId}")
    suspend fun delete(@Path("habitId") habitId: Long): retrofit2.Response<Unit>
}
