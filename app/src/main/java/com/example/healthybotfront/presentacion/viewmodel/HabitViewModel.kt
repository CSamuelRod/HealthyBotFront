package com.example.healthybotfront.presentacion.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.HabitDto
import com.example.healthybotfront.domain.usecase.habitUseCases.CreateHabitUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.DeleteHabitUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.GetHabitByIdUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.UpdateHabitUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitViewModel(
    private val createHabitUseCase: CreateHabitUseCase,
    private val getHabitsByUserIdUseCase: GetHabitsByUserIdUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase
) : ViewModel() {

    private val _habits = MutableStateFlow<List<HabitDto>>(emptyList())
    val habits = _habits.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _selectedHabit = mutableStateOf<HabitDto?>(null)
    val selectedHabit: State<HabitDto?> = _selectedHabit

    fun getHabits(userId: Long) {
        viewModelScope.launch {
            try {
                _habits.value = getHabitsByUserIdUseCase(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar hábitos"
            }
        }
    }

    fun createHabit(habitDto: HabitDto, onResult: (HabitDto) -> Unit) {
        viewModelScope.launch {
            try {
                val createdHabit = createHabitUseCase.invoke(habitDto)
                onResult(createdHabit)
            } catch (e: Exception) {
                _errorMessage.value = "Error al crear hábito: ${e.message}"
            }
        }
    }

    fun updateHabit(
        habitId: Long,
        name: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ){
        viewModelScope.launch {
            try {
                updateHabitUseCase(habitId, name, description)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al actualizar hábito")
            }
        }
    }

    fun loadHabitById(habitId: Long) {
        viewModelScope.launch {
            val habit = getHabitByIdUseCase(habitId)
            _selectedHabit.value = habit
        }
    }

    fun deleteHabit(habitId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                deleteHabitUseCase(habitId)
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar hábito: ${e.message}"
            }
        }
    }

}
