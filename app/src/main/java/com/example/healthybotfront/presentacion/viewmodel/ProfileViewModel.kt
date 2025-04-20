package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.UserDto
import com.example.healthybotfront.domain.usecase.DeleteUserUseCase
import com.example.healthybotfront.domain.usecase.GetUserUseCase
import com.example.healthybotfront.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
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
                // Llamamos al UseCase para hacer el update en la base de datos
                val updatedUser = updateUser.updateUser(userId, updated)
                _user.value = updatedUser
            } catch (e: Exception) {
                // Manejo de error en la actualización
                _errorMessage.value = "Error al actualizar el usuario: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
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
