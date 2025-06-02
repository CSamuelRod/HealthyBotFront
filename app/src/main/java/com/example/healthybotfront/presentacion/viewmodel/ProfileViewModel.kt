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

/**
 * ViewModel que maneja la lógica de negocio relacionada con el perfil del usuario.
 *
 * @property getUser Caso de uso para obtener los datos del usuario.
 * @property updateUser Caso de uso para actualizar los datos del usuario.
 * @property deleteUser Caso de uso para eliminar un usuario.
 */
class ProfileViewModel(
    private val getUser: GetUserUseCase,
    private val updateUser: UpdateUserUseCase,
    private val deleteUser: DeleteUserUseCase
) : ViewModel() {

    /** Estado que contiene los datos actuales del usuario, puede ser null si no se ha cargado aún. */
    private val _user = MutableStateFlow<UserDto?>(null)
    val user = _user.asStateFlow()

    /** Estado para manejar mensajes de error en las operaciones con usuario. */
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    /** Estado que indica si se está realizando una operación que toma tiempo (cargando). */
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    /** Estado que indica si la actualización del usuario fue exitosa. */
    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess

    /**
     * Carga los datos del usuario dado su ID.
     *
     * @param userId ID del usuario a cargar.
     */
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

    /**
     * Actualiza los datos del usuario con la información proporcionada.
     *
     * @param userId ID del usuario a actualizar.
     * @param updated Datos actualizados del usuario.
     */
    fun updateUser(userId: Long, updated: UserDto) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val updatedUser = updateUser.updateUser(userId, updated)
                _user.value = updatedUser
                _errorMessage.value = null
                _updateSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = when {
                    e is HttpException && e.code() == 409 ->
                        "El correo ya está registrado en otro usuario."
                    e.message?.contains("Correo ya existente") == true ->
                        "El correo ya está registrado en otro usuario."
                    else ->
                        "Error al actualizar el usuario: ${e.message}"
                }
                _updateSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Reinicia el flag que indica que la actualización fue exitosa,
     * para ser usado después de navegar o mostrar un mensaje.
     */
    fun resetUpdateSuccess() {
        _updateSuccess.value = false
    }

    /**
     * Elimina al usuario indicado por su ID.
     *
     * @param userId ID del usuario a eliminar.
     * @param onSuccess Callback a ejecutar en caso de eliminación exitosa.
     */
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
