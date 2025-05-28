package com.example.healthybotfront.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.Worker
import com.example.healthybotfront.R

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    private val channelId = "daily_notification_channel"
    private val notificationId = 1

    override fun doWork(): Result {
        showNotification()
        Log.d("NotificationWorker", "Worker ejecutado")
        // Reprograma el siguiente día (si quieres)
        return Result.success()
    }

    private fun showNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Diarias",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("HealthyBot")
            .setContentText("Es hora de tu notificación diaria!")
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
