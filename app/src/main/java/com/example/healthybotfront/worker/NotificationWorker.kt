package com.example.healthybotfront.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.healthybotfront.R
import com.example.healthybotfront.data.scheduler.DailyNotificationScheduler

/**
 * Worker que se encarga de mostrar una notificación diaria para recordar
 * al usuario registrar sus hábitos.
 *
 * También se encarga de reprogramar la siguiente notificación utilizando la hora
 * configurada por el usuario.
 *
 * @param context Contexto de la aplicación
 * @param params Parámetros de trabajo que pueden incluir la hora y minuto configurados
 */
class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    // ID del canal de notificación (importante para Android 8.0+)
    private val channelId = "daily_notification_channel"
    // ID único para la notificación, usado para identificarla en el sistema
    private val notificationId = 1

    /**
     * Método principal que se ejecuta cuando el worker es invocado.
     * Muestra la notificación al usuario y reprograma la siguiente notificación.
     *
     * @return Result.success() si la tarea se ejecuta correctamente
     */
    override fun doWork(): Result {
        // Obtiene el servicio de notificaciones del sistema
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Para Android Oreo (API 26) o superior, crea un canal de notificación si no existe
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Diarias",  // Nombre visible del canal
                NotificationManager.IMPORTANCE_HIGH  // Alta importancia para alertar al usuario
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Construye la notificación con título, texto, icono y configuración
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("HealthyBot")
            .setContentText("¡Es hora de registrar tus hábitos diarios!")
            .setSmallIcon(R.drawable.ic_notification)  // Icono de la app para la notificación
            .setAutoCancel(true)                        // Se borra al tocarla
            .build()

        // Envía la notificación al sistema para mostrarla al usuario
        notificationManager.notify(notificationId, notification)

        // Reprogramar la siguiente notificación usando la hora configurada por el usuario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Obtiene la hora y minuto guardados en los datos de entrada o usa valores por defecto
            val hour = inputData.getInt("hour", 9)    // hora por defecto 9
            val minute = inputData.getInt("minute", 0)

            // Usa el scheduler para programar la siguiente notificación diaria
            val scheduler = DailyNotificationScheduler()
            scheduler.schedule(context, hour, minute)
        }

        // Indica que la tarea terminó exitosamente
        return Result.success()
    }
}
