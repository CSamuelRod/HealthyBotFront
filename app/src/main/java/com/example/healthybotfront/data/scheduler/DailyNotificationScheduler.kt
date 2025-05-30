package com.example.healthybotfront.data.scheduler

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.healthybotfront.worker.NotificationWorker
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class DailyNotificationScheduler {

    @RequiresApi(Build.VERSION_CODES.O)
    fun schedule(context: Context, hour: Int, minute: Int) {
        val delay = calculateDelayUntil(hour, minute)

        val inputData = workDataOf(
            "hour" to hour,
            "minute" to minute
        )

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag("daily_notification")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "daily_notification",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDelayUntil(hour: Int, minute: Int): Long {
        val now = LocalDateTime.now()
        var target = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        if (target.isBefore(now)) target = target.plusDays(1)
        return Duration.between(now, target).toMillis()
    }
}