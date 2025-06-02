package com.example.healthybotfront.data.scheduler

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.healthybotfront.worker.NotificationWorker
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

/**
 * Clase encargada de programar notificaciones diarias usando WorkManager.
 *
 * Permite configurar una notificación para que se dispare todos los días a una hora y minuto específicos.
 */
class DailyNotificationScheduler {

    /**
     * Programa una notificación diaria a la hora y minuto indicados.
     *
     * @param context Contexto de la aplicación para acceder a WorkManager
     * @param hour Hora del día (0-23) a la que se desea programar la notificación
     * @param minute Minuto (0-59) a la que se desea programar la notificación
     *
     * @throws java.lang.UnsupportedOperationException si la versión de Android es anterior a Oreo (API 26)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun schedule(context: Context, hour: Int, minute: Int) {
        // Calcula el retraso en milisegundos hasta la próxima hora y minuto especificados
        val delay = calculateDelayUntil(hour, minute)

        // Crea un objeto Data para pasar los parámetros hora y minuto al Worker
        val inputData = workDataOf(
            "hour" to hour,
            "minute" to minute
        )

        // Construye una solicitud de trabajo único que se ejecutará una vez tras el retraso calculado
        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)  // Retrasa la ejecución hasta la hora programada
            .setInputData(inputData)                         // Pasa los datos de hora y minuto al Worker
            .addTag("daily_notification")                    // Etiqueta para identificar este trabajo
            .build()
        // Encola la tarea en WorkManager asegurando que solo haya una tarea con esta etiqueta (se reemplaza la anterior si existe)
        WorkManager.getInstance(context).enqueueUniqueWork(
            "daily_notification",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    /**
     * Calcula el tiempo en milisegundos que falta desde el momento actual hasta la siguiente
     * ocurrencia de la hora y minuto especificados.
     *
     * Si la hora y minuto indicados ya pasaron en el día actual, calcula el tiempo hasta el día siguiente.
     *
     * @param hour Hora objetivo (0-23)
     * @param minute Minuto objetivo (0-59)
     * @return Tiempo en milisegundos hasta la siguiente ocurrencia de la hora y minuto indicados
     *
     * @throws java.lang.UnsupportedOperationException si la versión de Android es anterior a Oreo (API 26)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDelayUntil(hour: Int, minute: Int): Long {
        val now = LocalDateTime.now() // Obtiene la fecha y hora actual
        var target = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0) // Define el objetivo para hoy con hora y minuto indicados
        if (target.isBefore(now)) target = target.plusDays(1) // Si la hora ya pasó hoy, apunta al mismo horario del día siguiente
        return Duration.between(now, target).toMillis() // Calcula la diferencia en milisegundos
    }
}
