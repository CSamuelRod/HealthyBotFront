package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.GoalDto
import com.example.healthybotfront.domain.usecase.goalUseCases.CreateGoalUseCase
import com.example.healthybotfront.domain.usecase.goalUseCases.DeleteGoalUseCase
import com.example.healthybotfront.domain.usecase.goalUseCases.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.goalUseCases.UpdateGoalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de manejar la lógica relacionada con las metas (goals).
 *
 * @property getGoalByHabitId Caso de uso para obtener una meta a partir del ID del hábito.
 * @property createGoal Caso de uso para crear una nueva meta.
 * @property updateGoal Caso de uso para actualizar una meta existente.
 * @property deleteGoal Caso de uso para eliminar una meta.
 */
class GoalViewModel(
    private val getGoalByHabitId: GetGoalByHabitIdUseCase,
    private val createGoal: CreateGoalUseCase,
    private val updateGoal: UpdateGoalUseCase,
    private val deleteGoal: DeleteGoalUseCase
) : ViewModel() {

    /** Estado que contiene la meta actual, puede ser null si no hay meta cargada */
    private val _goalState = MutableStateFlow<GoalDto?>(null)
    val goalState: StateFlow<GoalDto?> = _goalState

    /**
     * Obtiene la meta asociada a un hábito dado.
     *
     * @param habitId ID del hábito para el cual se quiere obtener la meta.
     */
    fun fetchGoal(habitId: Long) {
        viewModelScope.launch {
            _goalState.value = getGoalByHabitId(habitId)
        }
    }

    /**
     * Crea una nueva meta.
     *
     * @param goal Meta a crear.
     */
    fun create(goal: GoalDto) {
        viewModelScope.launch {
            _goalState.value = createGoal(goal)
        }
    }

    /**
     * Actualiza una meta existente.
     *
     * @param goalId ID de la meta a actualizar.
     * @param goal Datos actualizados de la meta.
     */
    fun update(goalId: Long, goal: GoalDto) {
        viewModelScope.launch {
            _goalState.value = updateGoal(goalId, goal)
        }
    }

    /**
     * Elimina una meta por su ID.
     *
     * @param goalId ID de la meta a eliminar.
     */
    fun delete(goalId: Long) {
        viewModelScope.launch {
            deleteGoal(goalId)
            _goalState.value = null
        }
    }
}
