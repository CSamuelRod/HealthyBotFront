package com.example.healthybotfront.domain.usecase.notificationsUseCases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthybotfront.data.scheduler.DailyNotificationScheduler
import com.example.healthybotfront.domain.model.NotificationTime

/**
 * Caso de uso para programar una notificación diaria en el sistema,
 * usando la hora configurada por el usuario.
 *
 * @property scheduler Instancia del programador de notificaciones diarias.
 */
class ScheduleNotificationUseCase(
    private val scheduler: DailyNotificationScheduler
) {

    /**
     * Programa la notificación diaria para la hora indicada.
     *
     * @param context Contexto necesario para acceder a servicios del sistema.
     * @param time Objeto que contiene la hora y minuto para programar la notificación.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun invoke(context: Context, time: NotificationTime) {
        scheduler.schedule(context, time.hour, time.minute)
    }
}
