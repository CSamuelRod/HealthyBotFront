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

/**
 * ViewModel encargado de manejar la lógica relacionada con la configuración
 * del horario de notificaciones de la aplicación.
 *
 * @property getNotificationTimeUseCase Caso de uso para obtener la hora de notificación guardada.
 * @property setNotificationTimeUseCase Caso de uso para guardar la hora de notificación seleccionada.
 */
class NotificationViewModel(
    private val getNotificationTimeUseCase: GetNotificationTimeUseCase,
    private val setNotificationTimeUseCase: SetNotificationTimeUseCase
) : ViewModel() {

    /**
     * Estado que expone la hora configurada para la notificación.
     * Inicialmente se establece a las 9:00 AM.
     */
    private val _notificationTime = MutableStateFlow(NotificationTime(9, 0))
    val notificationTime: StateFlow<NotificationTime> = _notificationTime

    init {
        // Carga la hora guardada al iniciar el ViewModel
        viewModelScope.launch {
            _notificationTime.value = getNotificationTimeUseCase()
        }
    }

    /**
     * Actualiza la hora de la notificación y la guarda usando el caso de uso correspondiente.
     *
     * @param context Contexto necesario para el caso de uso que guarda la hora.
     * @param hour Hora seleccionada para la notificación (0-23).
     * @param minute Minutos seleccionados para la notificación (0-59).
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setNotificationTime(context: Context, hour: Int, minute: Int) {
        val time = NotificationTime(hour, minute)
        viewModelScope.launch {
            setNotificationTimeUseCase(context, time)
            _notificationTime.value = time
        }
    }
}
