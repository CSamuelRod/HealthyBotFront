package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.AuthUseCases.RegisterUseCase
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

    private val _isRegistrationSuccessful = MutableStateFlow(false)
    val isRegistrationSuccessful: StateFlow<Boolean> = _isRegistrationSuccessful

    private fun areFieldsValid(): String? {
        if (_name.value.isBlank() || _lastname.value.isBlank() || _email.value.isBlank() || _password.value.isBlank()) {
            return "Todos los campos son obligatorios"
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches() ||
            !(_email.value.endsWith("@gmail.com") || _email.value.endsWith("@hotmail.com"))) {
            return "El correo debe ser válido y terminar en @gmail.com o @hotmail.com"
        }

        if (_password.value.length < 6) {
            return "La contraseña debe tener al menos 6 caracteres"
        }

        if (!Regex(".*[A-Za-z].*").containsMatchIn(_password.value) ||
            !Regex(".*[0-9].*").containsMatchIn(_password.value)) {
            return "La contraseña debe contener letras y números"
        }

        return null
    }


    fun register() {
        viewModelScope.launch {
            val validationError = areFieldsValid()
            if (validationError != null) {
                _registerState.value = "Error: $validationError"
                _isRegistrationSuccessful.value = false
                return@launch
            }

            try {
                val response = registerUseCase.invoke(_name.value, _lastname.value, _email.value, _password.value)

                if (response.contains("registrado con éxito", ignoreCase = true)) {
                    _registerState.value = "Registro exitoso: $response"
                    _isRegistrationSuccessful.value = true
                } else {
                    _registerState.value = "Error: $response"
                    _isRegistrationSuccessful.value = false
                }
            } catch (e: Exception) {
                _registerState.value = "Error: ${e.message}"
                _isRegistrationSuccessful.value = false
            }
        }
    }


}
