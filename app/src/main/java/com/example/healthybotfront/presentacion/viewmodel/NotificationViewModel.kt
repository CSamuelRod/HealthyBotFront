package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.notificationsUseCases.GetNotificationTimeUseCase
import com.example.healthybotfront.domain.usecase.notificationsUseCases.SetNotificationTimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.content.Context
import androidx.work.*
import com.example.healthybotfront.data.worker.NotificationWorker
import java.util.concurrent.TimeUnit
import java.util.Calendar

class NotificationViewModel(
    private val setNotificationTimeUseCase: SetNotificationTimeUseCase,
    private val getNotificationTimeUseCase: GetNotificationTimeUseCase
) : ViewModel() {

    private val _notificationTime = MutableStateFlow<Pair<Int, Int>>(9 to 0)
    val notificationTime: StateFlow<Pair<Int, Int>> = _notificationTime

    init {
        viewModelScope.launch {
            getNotificationTimeUseCase().collect {
                _notificationTime.value = it
            }
        }
    }

    fun scheduleDailyNotification(context: Context, hour: Int, minute: Int) {
        val now = Calendar.getInstance()
        val due = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (due.before(now)) {
            due.add(Calendar.DAY_OF_MONTH, 1)
        }

        val delay = due.timeInMillis - now.timeInMillis

        val dailyWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag("daily_notification")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "daily_notification_work",
            ExistingWorkPolicy.REPLACE,
            dailyWorkRequest
        )
    }



    fun setNotificationTime(context: Context, hour: Int, minute: Int) {
        viewModelScope.launch {
            setNotificationTimeUseCase(hour, minute)
            scheduleDailyNotification(context, hour, minute)
        }
    }


}
