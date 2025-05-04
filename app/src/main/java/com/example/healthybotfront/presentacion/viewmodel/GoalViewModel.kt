package com.example.healthybotfront.presentacion.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.GoalDto
import com.example.healthybotfront.domain.usecase.CreateGoalUseCase
import com.example.healthybotfront.domain.usecase.DeleteGoalUseCase
import com.example.healthybotfront.domain.usecase.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.UpdateGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoalViewModel(
    private val getGoalByHabitId: GetGoalByHabitIdUseCase,
    private val createGoal: CreateGoalUseCase,
    private val updateGoal: UpdateGoalUseCase,
    private val deleteGoal: DeleteGoalUseCase
) : ViewModel() {

    private val _goalState = MutableStateFlow<GoalDto?>(null)
    val goalState: StateFlow<GoalDto?> = _goalState

    fun fetchGoal(habitId: Long) {
        viewModelScope.launch {
            _goalState.value = getGoalByHabitId(habitId)
        }
    }

    fun create(goal: GoalDto) {
        viewModelScope.launch {
            _goalState.value = createGoal(goal)
        }
    }

    fun update(goalId: Long, goal: GoalDto) {
        viewModelScope.launch {
            _goalState.value = updateGoal(goalId, goal)
        }
    }

    fun delete(goalId: Long) {
        viewModelScope.launch {
            deleteGoal(goalId)
            _goalState.value = null
        }
    }
}
