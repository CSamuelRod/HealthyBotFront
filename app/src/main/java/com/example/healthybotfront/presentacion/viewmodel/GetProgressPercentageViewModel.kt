package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import com.example.healthybotfront.domain.usecase.progressUseCases.GetProgressPercentageByUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetProgressPercentageViewModel(
    private val getProgressPercentageByUserUseCase: GetProgressPercentageByUserUseCase
) : ViewModel() {

    private val _percentage = MutableStateFlow<List<ProgressPercentageDto>>(emptyList())
    val percentage: StateFlow<List<ProgressPercentageDto>> get() = _percentage

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadPercentage(userId: Long) {
        viewModelScope.launch {
            try {
                _percentage.value = getProgressPercentageByUserUseCase(userId)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            }
        }
    }
}
