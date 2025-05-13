package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.HabitDto
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.domain.usecase.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.GetProgressByHabitIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GetProgressViewModel (
    private val getHabitsByUserIdUseCase: GetHabitsByUserIdUseCase,
    private val getProgressByHabitIdUseCase: GetProgressByHabitIdUseCase
) : ViewModel() {

    private val _habits = MutableStateFlow<List<HabitDto>>(emptyList())
    val habits: StateFlow<List<HabitDto>> = _habits.asStateFlow()

    private val _progressList = MutableStateFlow<List<ProgressDto>>(emptyList())
    val progressList: StateFlow<List<ProgressDto>> = _progressList.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val combinedMonthlyProgress: StateFlow<Float> = progressList.map { progress ->
        val totalCompleted = progress.count { it.completed }
        val totalExpected = progress.size
        if (totalExpected > 0) totalCompleted.toFloat() / totalExpected else 0f
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    fun loadUserProgress(userId: Long) {
        viewModelScope.launch {
            try {
                val userHabits = getHabitsByUserIdUseCase(userId)
                _habits.value = userHabits

                val allProgress = userHabits.flatMap { habit ->
                    // Aquí ya estás llamando a un endpoint que filtra por mes actual
                    getProgressByHabitIdUseCase(habit.habitId ?: 0L)
                }

                _progressList.value = allProgress
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error al cargar progreso: ${e.message}"
            }
        }
    }
}