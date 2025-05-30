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

class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    private val channelId = "daily_notification_channel"
    private val notificationId = 1

    override fun doWork(): Result {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Diarias",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("HealthyBot")
            .setContentText("¡Es hora de registrar tus hábitos diarios!")
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)

        // Reprogramar la siguiente notificación usando la hora del usuario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val hour = inputData.getInt("hour", 9)    // hora por defecto 9
            val minute = inputData.getInt("minute", 0)

            val scheduler = DailyNotificationScheduler()
            scheduler.schedule(context, hour, minute)
        }

        return Result.success()
    }
}
