package com.example.healthybotfront.presentacion.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.ProgressDto
import com.example.healthybotfront.domain.usecase.GetGoalByHabitIdUseCase
import com.example.healthybotfront.domain.usecase.SaveProgressUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProgressViewModel(
    private val saveProgressUseCase: SaveProgressUseCase,
    private val getGoalByHabitIdUseCase: GetGoalByHabitIdUseCase
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveProgress(habitId: Long, completed: Boolean, notes: String?, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                var dto: ProgressDto? = null
                val goal = getGoalByHabitIdUseCase.invoke(habitId)
                if (goal != null) {
                    if (goal.goalId != null) {
                        dto = ProgressDto(goal.goalId,
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
}
