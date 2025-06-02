package com.example.healthybotfront.domain.usecase.notificationsUseCases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthybotfront.domain.model.NotificationTime
import com.example.healthybotfront.domain.repository.NotificationTimeRepository
import com.example.healthybotfront.data.scheduler.DailyNotificationScheduler

/**
 * Caso de uso para establecer el horario de notificación diaria.
 * Guarda el tiempo configurado y programa la notificación con ese horario.
 *
 * @property repository Repositorio para guardar y recuperar el tiempo de notificación.
 * @property scheduler Programador de notificaciones diarias.
 */
class SetNotificationTimeUseCase(
    private val repository: NotificationTimeRepository,
    private val scheduler: DailyNotificationScheduler
) {
    /**
     * Guarda el tiempo de notificación y programa la notificación diaria.
     *
     * @param context Contexto necesario para acceder a servicios del sistema.
     * @param time Objeto con la hora y minuto para configurar la notificación.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(context: Context, time: NotificationTime) {
        repository.saveTime(time)
        scheduler.schedule(context, time.hour, time.minute)
    }
}
