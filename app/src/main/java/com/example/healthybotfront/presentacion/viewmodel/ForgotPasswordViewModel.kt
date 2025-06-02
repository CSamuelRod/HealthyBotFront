package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.userUseCases.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de manejar la lógica de recuperación de contraseña.
 *
 * @property resetPasswordUseCase Caso de uso para restablecer la contraseña de un usuario.
 */
class ForgotPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    /** Estado que mantiene el email ingresado por el usuario */
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    /** Estado que mantiene la nueva contraseña ingresada por el usuario */
    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword

    /** Estado que mantiene el mensaje de resultado de la operación */
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    /**
     * Actualiza el valor del email.
     *
     * @param value Nuevo email ingresado.
     */
    fun setEmail(value: String) {
        _email.value = value
    }

    /**
     * Actualiza el valor de la nueva contraseña.
     *
     * @param value Nueva contraseña ingresada.
     */
    fun setNewPassword(value: String) {
        _newPassword.value = value
    }

    /**
     * Ejecuta la lógica para resetear la contraseña utilizando el caso de uso.
     * Actualiza el mensaje con el resultado de la operación.
     */
    fun resetPassword() {
        viewModelScope.launch {
            try {
                val response = resetPasswordUseCase(_email.value, _newPassword.value)
                if (response.email == "email no encontrado") {
                    _message.value = "Error: correo no encontrado"
                } else {
                    _message.value = "Contraseña actualizada con éxito"
                }
            } catch (e: Exception) {
                _message.value = "Error: ${e.message}"
            }
        }
    }

}
