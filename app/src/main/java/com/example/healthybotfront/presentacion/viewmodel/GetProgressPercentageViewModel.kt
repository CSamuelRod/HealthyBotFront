package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.ProgressPercentageDto
import com.example.healthybotfront.domain.usecase.progressUseCases.GetProgressPercentageByUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * ViewModel encargado de obtener el porcentaje de progreso de un usuario.
 *
 * @property getProgressPercentageByUserUseCase Caso de uso para obtener el porcentaje de progreso por usuario.
 */
class GetProgressPercentageViewModel(
    private val getProgressPercentageByUserUseCase: GetProgressPercentageByUserUseCase
) : ViewModel() {

    /** Estado que contiene la lista de porcentajes de progreso del usuario */
    private val _percentage = MutableStateFlow<List<ProgressPercentageDto>>(emptyList())
    val percentage: StateFlow<List<ProgressPercentageDto>> get() = _percentage

    /** Estado que contiene el mensaje de error, si ocurre alguno */
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Carga el porcentaje de progreso para un usuario dado.
     *
     * @param userId Identificador del usuario.
     *
     * Maneja errores HTTP 404 ocultando el error y retornando una lista vacía.
     * Otros errores se informan a través del estado [_error].
     */
    fun loadPercentage(userId: Long) {
        viewModelScope.launch {
            try {
                _percentage.value = getProgressPercentageByUserUseCase(userId)
                _error.value = null
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _percentage.value = emptyList() // Sin progreso disponible
                    _error.value = null // No mostrar error
                } else {
                    _error.value = "Error al obtener progreso: ${e.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error desconocido: ${e.message}"
            }
        }
    }
}
