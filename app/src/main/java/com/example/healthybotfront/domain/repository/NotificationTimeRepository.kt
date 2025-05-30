package com.example.healthybotfront.domain.repository

import android.content.Context
import com.example.healthybotfront.domain.model.NotificationTime

interface NotificationTimeRepository {
    suspend fun saveTime(time: NotificationTime)
    suspend fun getTime(): NotificationTime
}