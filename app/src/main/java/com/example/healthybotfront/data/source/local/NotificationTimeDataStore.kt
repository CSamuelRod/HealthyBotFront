package com.example.healthybotfront.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.healthybotfront.domain.model.NotificationTime
import com.example.healthybotfront.domain.repository.NotificationTimeRepository
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "notification_prefs")

class NotificationTimeRepositoryImpl(private val context: Context) : NotificationTimeRepository {

    private val hourKey = intPreferencesKey("hour")
    private val minuteKey = intPreferencesKey("minute")

    override suspend fun saveTime(time: NotificationTime) {
        context.dataStore.edit { prefs ->
            prefs[hourKey] = time.hour
            prefs[minuteKey] = time.minute
        }
    }

    override suspend fun getTime(): NotificationTime {
        val prefs = context.dataStore.data.first()
        val hour = prefs[hourKey] ?: 9
        val minute = prefs[minuteKey] ?: 0
        return NotificationTime(hour, minute)
    }
}
