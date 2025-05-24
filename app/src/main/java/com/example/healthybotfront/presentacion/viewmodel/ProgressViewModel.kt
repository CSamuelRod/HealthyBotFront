package com.example.healthybotfront.presentacion.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.domain.usecase.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.GetHabitsByUserIdUseCase
import com.example.healthybotfront.domain.usecase.GetProgressByUserAndDateUseCase
import com.example.healthybotfront.domain.usecase.DeleteProgressUseCase
import com.example.healthybotfront.domain.usecase.SaveProgressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProgressViewModel(
    private val saveProgressUseCase: SaveProgressUseCase,
    private val getGoalByHabitIdUseCase: GetGoalByHabitIdUseCase,
    private val deleteProgressUseCase: DeleteProgressUseCase,
    private val getProgressByUserAndDateUseCase: GetProgressByUserAndDateUseCase,
    private val getHabitsByUserIdUseCase: GetHabitsByUserIdUseCase
) : ViewModel() {
    // Dentro del ProgressViewModel


    private val _dailyProgress = MutableStateFlow<Map<Long, ProgressDto>>(emptyMap()) // habitId -> ProgressDto
    val dailyProgress = _dailyProgress.asStateFlow()

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
                    }else{
                        println("error en creacion del progress dto");
                    }
                }
                if (dto != null) {
                    saveProgressUseCase(dto)
                } else {
                    onError("Error: No se pudo crear el ProgressDto")
                }
            } catch (e: Exception) {
                // Manejo de errores
                onError("Error al guardar el progreso: ${e.message}")
            }
        }
    }

    suspend fun loadDailyProgress(userId: Long, date: LocalDate) {
        // Obtener lista de progress para el user y la fecha
        val progressList = getProgressByUserAndDateUseCase.invoke(userId, date)

        // Obtener hábitos del usuario
        val habits = getHabitsByUserIdUseCase.invoke(userId)

        // Mapear goalId a habitId
        val goalIdToHabitIdMap = habits
            .filter { it.goalId != null && it.habitId != null }
            .associate { it.goalId!! to it.habitId!! }

        // Mapear progress (goalId) a habitId
        val map = progressList.mapNotNull { progress ->
            val habitId = goalIdToHabitIdMap[progress.goalId]
            habitId?.let { it to progress }
        }.toMap()

        _dailyProgress.value = map
    }




    fun deleteProgress(progressId: Long) {
        viewModelScope.launch {
            try {
                deleteProgressUseCase.invoke(progressId)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

}
