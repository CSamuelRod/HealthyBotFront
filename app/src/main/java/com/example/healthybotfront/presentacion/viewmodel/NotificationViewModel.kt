package com.example.healthybotfront.presentacion.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.model.NotificationTime
import com.example.healthybotfront.domain.usecase.notificationsUseCases.GetNotificationTimeUseCase
import com.example.healthybotfront.domain.usecase.notificationsUseCases.SetNotificationTimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getNotificationTimeUseCase: GetNotificationTimeUseCase,
    private val setNotificationTimeUseCase: SetNotificationTimeUseCase
) : ViewModel() {

    private val _notificationTime = MutableStateFlow(NotificationTime(9, 0))
    val notificationTime: StateFlow<NotificationTime> = _notificationTime

    init {
        viewModelScope.launch {
            _notificationTime.value = getNotificationTimeUseCase()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotificationTime(context: Context, hour: Int, minute: Int) {
        val time = NotificationTime(hour, minute)
        viewModelScope.launch {
            setNotificationTimeUseCase(context, time)
            _notificationTime.value = time
        }
    }
}