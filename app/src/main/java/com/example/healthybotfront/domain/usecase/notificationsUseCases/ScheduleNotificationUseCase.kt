package com.example.healthybotfront.domain.usecase.notificationsUseCases

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthybotfront.data.scheduler.DailyNotificationScheduler
import com.example.healthybotfront.domain.model.NotificationTime

class ScheduleNotificationUseCase(
    private val scheduler: DailyNotificationScheduler
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun invoke(context: Context, time: NotificationTime) {
        scheduler.schedule(context, time.hour, time.minute)
    }
}
