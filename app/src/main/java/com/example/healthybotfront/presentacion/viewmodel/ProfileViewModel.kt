package com.example.healthybotfront.presentacion.viewmodel

import retrofit2.HttpException

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.UserDto
import com.example.healthybotfront.domain.usecase.userUseCases.DeleteUserUseCase
import com.example.healthybotfront.domain.usecase.userUseCases.GetUserUseCase
import com.example.healthybotfront.domain.usecase.userUseCases.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUser: GetUserUseCase,
    private val updateUser: UpdateUserUseCase,
    private val deleteUser: DeleteUserUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<UserDto?>(null)
    val user = _user.asStateFlow()

    // Estado para manejar el error
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    // Estado para manejar el estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    // Cargar el usuario
    fun loadUser(userId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _user.value = getUser(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar el usuario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Actualizar usuario
    fun updateUser(userId: Long, updated: UserDto) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updatedUser = updateUser.updateUser(userId, updated)
                _user.value = updatedUser
                _errorMessage.value = null // Limpia errores previos
                _updateSuccess.value = true // Indica que la actualización fue exitosa
            } catch (e: Exception) {
                _errorMessage.value = when {
                    e is HttpException && e.code() == 409 ->
                        "El correo ya está registrado en otro usuario."
                    e.message?.contains("Correo ya existente") == true ->
                        "El correo ya está registrado en otro usuario."
                    else ->
                        "Error al actualizar el usuario: ${e.message}"
                }
                _updateSuccess.value = false // Resetea el flag en caso de error
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para resetear el flag después de navegar
    fun resetUpdateSuccess() {
        _updateSuccess.value = false
    }
    // Eliminar usuario
    fun deleteUser(userId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                deleteUser(userId)
                onSuccess()
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar el usuario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
