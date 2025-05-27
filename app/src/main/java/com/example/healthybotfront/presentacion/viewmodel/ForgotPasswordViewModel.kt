package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.userUseCases.ResetPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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
