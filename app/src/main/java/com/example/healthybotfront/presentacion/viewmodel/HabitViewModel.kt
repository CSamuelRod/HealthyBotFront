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

/**
 * ViewModel que gestiona la lógica de negocio relacionada con los hábitos.
 *
 * @property createHabitUseCase Caso de uso para crear un nuevo hábito.
 * @property getHabitsByUserIdUseCase Caso de uso para obtener los hábitos de un usuario.
 * @property deleteHabitUseCase Caso de uso para eliminar un hábito.
 * @property updateHabitUseCase Caso de uso para actualizar un hábito.
 * @property getHabitByIdUseCase Caso de uso para obtener un hábito por su ID.
 */
class HabitViewModel(
    private val createHabitUseCase: CreateHabitUseCase,
    private val getHabitsByUserIdUseCase: GetHabitsByUserIdUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase
) : ViewModel() {

    /** Lista de hábitos obtenidos para un usuario */
    private val _habits = MutableStateFlow<List<HabitDto>>(emptyList())
    val habits = _habits.asStateFlow()

    /** Mensajes de error para mostrar en UI */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    /** Hábito seleccionado o cargado para visualización o edición */
    private val _selectedHabit = mutableStateOf<HabitDto?>(null)
    val selectedHabit: State<HabitDto?> = _selectedHabit

    /**
     * Obtiene los hábitos de un usuario por su ID.
     *
     * @param userId ID del usuario.
     */
    fun getHabits(userId: Long) {
        viewModelScope.launch {
            try {
                _habits.value = getHabitsByUserIdUseCase(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar hábitos"
            }
        }
    }

    /**
     * Crea un nuevo hábito.
     *
     * @param habitDto Datos del hábito a crear.
     * @param onResult Callback que recibe el hábito creado.
     */
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

    /**
     * Actualiza un hábito existente.
     *
     * @param habitId ID del hábito a actualizar.
     * @param name Nuevo nombre del hábito.
     * @param description Nueva descripción del hábito.
     * @param onSuccess Callback ejecutado en caso de éxito.
     * @param onError Callback ejecutado en caso de error con mensaje de error.
     */
    fun updateHabit(
        habitId: Long,
        name: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                updateHabitUseCase(habitId, name, description)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al actualizar hábito")
            }
        }
    }

    /**
     * Carga un hábito por su ID y lo establece como seleccionado.
     *
     * @param habitId ID del hábito a cargar.
     */
    fun loadHabitById(habitId: Long) {
        viewModelScope.launch {
            val habit = getHabitByIdUseCase(habitId)
            _selectedHabit.value = habit
        }
    }

    /**
     * Elimina un hábito por su ID.
     *
     * @param habitId ID del hábito a eliminar.
     * @param onSuccess Callback ejecutado si la eliminación fue exitosa.
     */
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
