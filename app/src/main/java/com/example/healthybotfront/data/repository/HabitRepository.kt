package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.HabitApi
import com.example.healthybotfront.data.source.remote.dto.HabitDto

/**
 * Repositorio para manejar operaciones relacionadas con hábitos.
 *
 * @property habitApi API remota para realizar llamadas de red relacionadas con hábitos.
 */
class HabitRepository(
    private val habitApi: HabitApi
) {

    /**
     * Crea un nuevo hábito para un usuario específico.
     *
     * @param habit Objeto [HabitDto] con los datos del hábito a crear. Debe incluir userId.
     * @return [HabitDto] con el hábito creado.
     */
    suspend fun createHabit(habit: HabitDto): HabitDto {
        return habitApi.createHabit(habit.userId ?: throw IllegalArgumentException("userId no puede ser nulo"), habit)
    }

    /**
     * Obtiene un hábito por su ID.
     *
     * @param habitId ID del hábito a obtener.
     * @return [HabitDto] con la información del hábito o null si no se encuentra.
     */
    suspend fun getHabitById(habitId: Long): HabitDto? {
        return habitApi.getHabitById(habitId)
    }

    /**
     * Obtiene todos los hábitos asociados a un usuario.
     *
     * @param userId ID del usuario cuyos hábitos se quieren obtener.
     * @return Lista de [HabitDto] con los hábitos del usuario.
     */
    suspend fun getHabitsByUserId(userId: Long): List<HabitDto> {
        return habitApi.getHabitsByUserId(userId)
    }

    /**
     * Actualiza un hábito existente.
     *
     * @param habitId ID del hábito que se quiere actualizar.
     * @param habitDto Objeto con los nuevos datos para el hábito.
     * @return [HabitDto] con el hábito actualizado.
     */
    suspend fun updateHabit(habitId: Long, habitDto: HabitDto): HabitDto {
        return habitApi.updateHabit(habitId, habitDto)
    }

    /**
     * Elimina un hábito por su ID.
     *
     * @param habitId ID del hábito a eliminar.
     * @throws Exception si la respuesta del servidor no es exitosa.
     */
    suspend fun deleteHabitById(habitId: Long) {
        val response = habitApi.delete(habitId)
        if (!response.isSuccessful) {
            throw Exception("Error en el servidor al eliminar hábito")
        }
    }
}
