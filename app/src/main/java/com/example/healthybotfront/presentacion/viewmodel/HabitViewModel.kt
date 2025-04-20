package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.HabitDto
import com.example.healthybotfront.domain.usecase.CreateHabitUseCase
import com.example.healthybotfront.domain.usecase.GetHabitsByUserIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitViewModel(
    private val createHabitUseCase: CreateHabitUseCase,
    private val getHabitsByUserIdUseCase: GetHabitsByUserIdUseCase
) : ViewModel() {

    private val _habits = MutableStateFlow<List<HabitDto>>(emptyList())
    val habits = _habits.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getHabits(userId: Long) {
        viewModelScope.launch {
            try {
                _habits.value = getHabitsByUserIdUseCase(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar hábitos"
            }
        }
    }

    fun createHabit(habit: HabitDto, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                createHabitUseCase(habit)
                onSuccess()
                getHabits(habit.userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al crear hábito"
            }
        }
    }
}
