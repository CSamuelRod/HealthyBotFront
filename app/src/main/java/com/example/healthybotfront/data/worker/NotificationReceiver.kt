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

/**
 * BroadcastReceiver que escucha las alarmas para enviar notificaciones diarias
 * recordando al usuario registrar sus hábitos.
 */
class NotificationReceiver : BroadcastReceiver() {

    // ID del canal de notificación (importante para Android 8.0+)
    private val channelId = "daily_notification_channel"
    // ID único para la notificación, usado para identificarla en el sistema
    private val notificationId = 1

    /**
     * Método llamado cuando se recibe el broadcast.
     * Crea el canal de notificación si es necesario y muestra la notificación al usuario.
     *
     * @param context Contexto donde se recibe el broadcast
     * @param intent Intent recibido (no se usa directamente en esta implementación)
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NotificationReceiver", "onReceive llamado")

        // Verifica que el contexto no sea nulo, si es así termina el método
        context ?: run {
            Log.e("NotificationReceiver", "Contexto nulo en onReceive")
            return
        }

        // Obtiene el servicio de notificaciones del sistema
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Para Android Oreo (API 26) o superior, crea un canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Diarias",  // Nombre visible del canal
                NotificationManager.IMPORTANCE_HIGH  // Importancia alta para que suene y aparezca en pantalla
            )
            notificationManager.createNotificationChannel(channel)
            Log.d("NotificationReceiver", "Canal de notificación creado")
        }

        // Construye la notificación con título, texto, icono y configuración
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("HealthyBot")
            .setContentText("Registra tus hábitos de hoy!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // Alta prioridad para mostrar de inmediato
            .setSmallIcon(R.drawable.ic_notification)      // Icono de la app para la notificación
            .setAutoCancel(true)                            // Se borra al tocarla
            .build()

        // Envía la notificación al sistema para mostrarla al usuario
        notificationManager.notify(notificationId, notification)
        Log.d("NotificationReceiver", "Notificación enviada")
    }

    /**
     * Método auxiliar para disparar manualmente la notificación (usado para pruebas).
     *
     * @param context Contexto desde el cual se envía el broadcast
     */
    fun triggerTestNotification(context: Context) {
        Log.d("NotificationReceiver", "Trigger de notificación de prueba")
        val intent = Intent(context, NotificationReceiver::class.java)
        context.sendBroadcast(intent)  // Envía el broadcast para activar onReceive manualmente
    }
}
