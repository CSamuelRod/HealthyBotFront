package com.example.healthybotfront.data.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.healthybotfront.R

class NotificationReceiver : BroadcastReceiver() {

    private val channelId = "daily_notification_channel"
    private val notificationId = 1

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NotificationReceiver", "onReceive llamado")
        context ?: run {
            Log.e("NotificationReceiver", "Contexto nulo en onReceive")
            return
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Diarias",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
            Log.d("NotificationReceiver", "Canal de notificación creado")
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("HealthyBot")
            .setContentText("Registra tus hábitos de hoy!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
        Log.d("NotificationReceiver", "Notificación enviada")
    }

    // Método para disparar el broadcast manualmente (test)
    fun triggerTestNotification(context: Context) {
        Log.d("NotificationReceiver", "Trigger de notificación de prueba")
        val intent = Intent(context, NotificationReceiver::class.java)
        context.sendBroadcast(intent)
    }
}
