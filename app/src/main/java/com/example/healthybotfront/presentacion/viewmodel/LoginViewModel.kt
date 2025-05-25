package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.AuthUseCases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        viewModelScope.launch {
            // Validación local antes del login
            if (_email.value.isBlank() || _password.value.isBlank()) {
                _error.value = "Por favor, completa todos los campos"
                return@launch
            }

            if (!isValidEmail(_email.value)) {
                _error.value = "El correo no es válido"
                return@launch
            }

            try {
                val result = loginUseCase(_email.value, _password.value)
                _userId.value = result.second

                if (result.second == -1L) {
                    _error.value = result.first
                } else {
                    _error.value = "" // limpiar mensaje de error
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(email)
    }



}

