package com.example.healthybotfront.domain.repository

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para inicializar DataStore en Context
private val Context.dataStore by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val HOUR = intPreferencesKey("notification_hour")
    val MINUTE = intPreferencesKey("notification_minute")
}

class SettingsRepository(private val context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveNotificationTime(hour: Int, minute: Int) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.HOUR] = hour
            prefs[PreferencesKeys.MINUTE] = minute
        }
    }

    val notificationTimeFlow: Flow<Pair<Int, Int>> = dataStore.data
        .map { prefs ->
            val hour = prefs[PreferencesKeys.HOUR] ?: 9
            val minute = prefs[PreferencesKeys.MINUTE] ?: 0
            hour to minute
        }
}
