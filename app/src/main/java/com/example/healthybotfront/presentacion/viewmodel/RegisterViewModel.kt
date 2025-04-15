package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.data.source.remote.dto.RegisterRequest
import com.example.healthybotfront.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    // Estado del formulario
    private val _registerState = MutableStateFlow<String>("")
    val registerState: StateFlow<String> = _registerState

    // Los datos del formulario
    private val _name = MutableStateFlow("")
    private val _lastname = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val name: StateFlow<String> = _name
    val lastname: StateFlow<String> = _lastname
    val email: StateFlow<String> = _email
    val password: StateFlow<String> = _password

    // Usamos setters para actualizar cada campo del formulario
    fun setName(name: String) {
        _name.value = name
    }

    fun setLastname(lastname: String) {
        _lastname.value = lastname
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    // Función para registrar al usuario
    fun register() {
        viewModelScope.launch {
            try {
                // Pasamos los valores directamente al UseCase
                val response = registerUseCase.invoke(
                    _name.value,
                    _lastname.value,
                    _email.value,
                    _password.value
                )

                _registerState.value = "Registro exitoso: $response"
            } catch (e: Exception) {
                _registerState.value = "Error: ${e.message}"
            }
        }
    }
}
