package com.example.healthybotfront.domain.usecase.notificationsUseCases

import com.example.healthybotfront.domain.repository.SettingsRepository

// GetNotificationTimeUseCase.kt
import kotlinx.coroutines.flow.Flow

class GetNotificationTimeUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Pair<Int, Int>> = repository.notificationTimeFlow
}