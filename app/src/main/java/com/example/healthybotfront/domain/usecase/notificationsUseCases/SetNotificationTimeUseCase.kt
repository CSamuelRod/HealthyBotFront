package com.example.healthybotfront.domain.usecase.notificationsUseCases

import com.example.healthybotfront.domain.repository.SettingsRepository

// SetNotificationTimeUseCase.kt
class SetNotificationTimeUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(hour: Int, minute: Int) {
        repository.saveNotificationTime(hour, minute)
    }
}