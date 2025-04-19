package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.LoginUseCase
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

    private val _loginState = MutableStateFlow("")
    val loginState: StateFlow<String> = _loginState

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        viewModelScope.launch {
            try {
                val result = loginUseCase(_email.value, _password.value)
                _loginState.value = "Login exitoso: $result"
            } catch (e: Exception) {
                _loginState.value = "Error: ${e.message}"
            }
        }
    }
}

