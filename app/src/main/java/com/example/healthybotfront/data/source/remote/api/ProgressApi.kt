package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.time.LocalDate

/**
 * Interfaz que define las llamadas API relacionadas con el progreso de los hábitos.
 * Utiliza Retrofit para realizar peticiones HTTP.
 */
interface ProgressApi {

        /**
         * Guarda un registro de progreso enviado desde el cliente.
         *
         * @param progress DTO que contiene la información del progreso a guardar
         * @return DTO con la información del progreso guardado
         */
        @POST("/progress")
        suspend fun saveProgress(@Body progress: ProgressDto): ProgressDto

        /**
         * Obtiene un listado del porcentaje de progreso mensual para un usuario dado.
         *
         * @param userId ID del usuario cuyos porcentajes de progreso se solicitan
         * @return Lista de DTOs con los porcentajes de progreso mensual
         */
        @GET("/progress/habit/{userId}/progress-percentage")
        suspend fun getProgressPercentageByUserId(@Path("userId") userId: Long): List<ProgressPercentageDto>

        /**
         * Elimina un registro de progreso por su ID.
         *
         * @param id ID del progreso a eliminar
         */
        @DELETE("/progress/{id}")
        suspend fun delete(@Path("id") id: Long)

        /**
         * Obtiene los registros únicos de progreso de un usuario para una fecha específica.
         *
         * @param userId ID del usuario
         * @param date Fecha para la cual se desea obtener el progreso (formato ISO yyyy-MM-dd)
         * @return Lista de DTOs con los progresos encontrados para ese día
         */
        @GET("/progress/user/{userId}/date/{date}")
        suspend fun getProgressByUserAndDate(
                @Path("userId") userId: Long,
                @Path("date") date: LocalDate
        ): List<ProgressDto>

}
