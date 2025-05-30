package com.example.healthybotfront.domain.usecase.notificationsUseCases

import com.example.healthybotfront.domain.model.NotificationTime
import com.example.healthybotfront.domain.repository.NotificationTimeRepository

class GetNotificationTimeUseCase(private val repository: NotificationTimeRepository) {
    suspend operator fun invoke(): NotificationTime = repository.getTime()
}
