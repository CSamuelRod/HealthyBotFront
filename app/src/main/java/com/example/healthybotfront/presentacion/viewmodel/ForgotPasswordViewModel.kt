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

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setNewPassword(value: String) {
        _newPassword.value = value
    }

    /**
     * Valida que la nueva contraseña cumpla con los requisitos.
     *
     * @return Mensaje de error si es inválida, o null si es válida.
     */
    private fun isPasswordValid(password: String): String? {
        if (password.length < 6) {
            return "La contraseña debe tener al menos 6 caracteres"
        }

        if (!Regex(".*[A-Za-z].*").containsMatchIn(password) ||
            !Regex(".*[0-9].*").containsMatchIn(password)) {
            return "La contraseña debe contener letras y números"
        }

        return null
    }

    /**
     * Ejecuta la lógica para resetear la contraseña utilizando el caso de uso.
     * Aplica validaciones antes de enviar la solicitud.
     */
    fun resetPassword() {
        viewModelScope.launch {
            val validationError = isPasswordValid(_newPassword.value)
            if (validationError != null) {
                _message.value = "Error: $validationError"
            // Si la contraseña no cumple los requisitos, se muestra un error
            // y se detiene la ejecución de esta operación sin seguir al siguiente paso.
                return@launch
            }

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
