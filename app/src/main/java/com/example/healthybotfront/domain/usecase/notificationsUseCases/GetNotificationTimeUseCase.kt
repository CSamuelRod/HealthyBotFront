package com.example.healthybotfront.domain.usecase.notificationsUseCases

import com.example.healthybotfront.domain.model.NotificationTime
import com.example.healthybotfront.domain.repository.NotificationTimeRepository

/**
 * Caso de uso para obtener la hora configurada para las notificaciones diarias.
 *
 * @property repository Repositorio encargado de manejar la persistencia de la hora de notificación.
 */
class GetNotificationTimeUseCase(private val repository: NotificationTimeRepository) {

    /**
     * Obtiene la hora de notificación configurada.
     *
     * @return La hora de notificación como un objeto [NotificationTime].
     */
    suspend operator fun invoke(): NotificationTime = repository.getTime()
}
