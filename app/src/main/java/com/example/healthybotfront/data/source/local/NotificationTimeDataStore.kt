package com.example.healthybotfront.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.healthybotfront.domain.model.NotificationTime
import com.example.healthybotfront.domain.repository.NotificationTimeRepository
import kotlinx.coroutines.flow.first

// Delegado para crear un DataStore de preferencias llamado "notification_prefs" asociado al Contexto
private val Context.dataStore by preferencesDataStore(name = "notification_prefs")

/**
 * Implementación de [NotificationTimeRepository] que utiliza DataStore para
 * almacenar y recuperar la hora y minuto configurados para notificaciones.
 *
 * @property context Contexto de la aplicación para acceder a DataStore
 */
class NotificationTimeRepositoryImpl(private val context: Context) : NotificationTimeRepository {

    // Claves para almacenar la hora y minuto en DataStore
    private val hourKey = intPreferencesKey("hour")
    private val minuteKey = intPreferencesKey("minute")

    /**
     * Guarda la hora y minuto para la notificación en DataStore.
     *
     * @param time Objeto [NotificationTime] que contiene la hora y minuto a guardar
     */
    override suspend fun saveTime(time: NotificationTime) {
        // Edita las preferencias del DataStore de forma asíncrona para guardar las claves con sus valores
        context.dataStore.edit { prefs ->
            prefs[hourKey] = time.hour    // Guarda la hora bajo la clave "hour"
            prefs[minuteKey] = time.minute // Guarda el minuto bajo la clave "minute"
        }
    }

    /**
     * Obtiene la hora y minuto almacenados para la notificación desde DataStore.
     * Si no se encuentra ningún valor, devuelve la hora 9 y minuto 0 como valores por defecto.
     *
     * @return [NotificationTime] con la hora y minuto guardados o valores por defecto
     */
    override suspend fun getTime(): NotificationTime {
        // Obtiene el flujo de datos y extrae la primera (y única) preferencia almacenada
        val prefs = context.dataStore.data.first()
        // Obtiene la hora almacenada o 9 si no existe
        val hour = prefs[hourKey] ?: 9
        // Obtiene el minuto almacenado o 0 si no existe
        val minute = prefs[minuteKey] ?: 0
        // Retorna un objeto NotificationTime con la hora y minuto obtenidos
        return NotificationTime(hour, minute)
    }
}
