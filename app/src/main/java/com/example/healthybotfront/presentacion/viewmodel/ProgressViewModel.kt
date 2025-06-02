package com.example.healthybotfront.presentacion.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.domain.usecase.goalUseCases.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.habitUseCases.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.GetProgressByUserAndDateUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.DeleteProgressUseCase
import com.example.healthybotfront.domain.usecase.progressUseCases.SaveProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel que maneja la lógica para gestionar el progreso diario de los hábitos de un usuario.
 *
 * @property saveProgressUseCase Caso de uso para guardar el progreso de un hábito.
 * @property getGoalByHabitIdUseCase Caso de uso para obtener la meta asociada a un hábito.
 * @property deleteProgressUseCase Caso de uso para eliminar un progreso.
 * @property getProgressByUserAndDateUseCase Caso de uso para obtener el progreso de un usuario en una fecha específica.
 * @property getHabitsByUserIdUseCase Caso de uso para obtener los hábitos de un usuario.
 */
class ProgressViewModel(
    private val saveProgressUseCase: SaveProgressUseCase,
    private val getGoalByHabitIdUseCase: GetGoalByHabitIdUseCase,
    private val deleteProgressUseCase: DeleteProgressUseCase,
    private val getProgressByUserAndDateUseCase: GetProgressByUserAndDateUseCase,
    private val getHabitsByUserIdUseCase: GetHabitsByUserIdUseCase
) : ViewModel() {

    /** Mapa que relaciona el ID del hábito con el progreso diario correspondiente. */
    private val _dailyProgress = MutableStateFlow<Map<Long, ProgressDto>>(emptyMap())
    val dailyProgress = _dailyProgress.asStateFlow()

    /**
     * Guarda el progreso diario de un hábito.
     *
     * @param habitId ID del hábito.
     * @param completed Indica si el hábito fue completado.
     * @param notes Notas adicionales sobre el progreso.
     * @param onError Callback para manejar errores en la operación.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveProgress(habitId: Long, completed: Boolean, notes: String?, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                var dto: ProgressDto? = null
                val goal = getGoalByHabitIdUseCase.invoke(habitId)
                if (goal != null) {
                    if (goal.goalId != null) {
                        dto = ProgressDto(
                            progressId = null,
                            goal.goalId,
                            LocalDate.now().toString(),
                            completed,
                            notes
                        )
                    } else {
                        println("error en creacion del progress dto")
                    }
                }
                if (dto != null) {
                    saveProgressUseCase(dto)
                } else {
                    onError("Error: No se pudo crear el ProgressDto")
                }
            } catch (e: Exception) {
                onError("Error al guardar el progreso: ${e.message}")
            }
        }
    }

    /**
     * Carga el progreso diario del usuario para una fecha específica y lo asocia a los hábitos correspondientes.
     *
     * @param userId ID del usuario.
     * @param date Fecha para la cual se obtiene el progreso.
     */
    suspend fun loadDailyProgress(userId: Long, date: LocalDate) {
        val progressList = getProgressByUserAndDateUseCase.invoke(userId, date)
        val habits = getHabitsByUserIdUseCase.invoke(userId)

        val goalIdToHabitIdMap = habits
            .filter { it.goalId != null && it.habitId != null }
            .associate { it.goalId!! to it.habitId!! }

        val map = progressList.mapNotNull { progress ->
            val habitId = goalIdToHabitIdMap[progress.goalId]
            habitId?.let { it to progress }
        }.toMap()

        _dailyProgress.value = map
    }

    /**
     * Elimina un progreso dado su ID.
     *
     * @param progressId ID del progreso a eliminar.
     */
    fun deleteProgress(progressId: Long) {
        viewModelScope.launch {
            try {
                deleteProgressUseCase.invoke(progressId)
            } catch (e: Exception) {
                // Manejar error si es necesario
            }
        }
    }
}
