package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.AuthUseCases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la lógica de registro de usuarios.
 *
 * @property registerUseCase Caso de uso para registrar un nuevo usuario.
 */
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    /** Estado que representa el resultado o error del registro */
    private val _registerState = MutableStateFlow<String>("")
    val registerState: StateFlow<String> = _registerState

    /** Campos del formulario de registro */
    private val _name = MutableStateFlow("")
    private val _lastname = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val name: StateFlow<String> = _name
    val lastname: StateFlow<String> = _lastname
    val email: StateFlow<String> = _email
    val password: StateFlow<String> = _password

    /** Flag que indica si el registro fue exitoso */
    private val _isRegistrationSuccessful = MutableStateFlow(false)
    val isRegistrationSuccessful: StateFlow<Boolean> = _isRegistrationSuccessful

    /**
     * Actualiza el nombre en el formulario.
     *
     * @param name Nuevo nombre.
     */
    fun setName(name: String) {
        _name.value = name
    }

    /**
     * Actualiza el apellido en el formulario.
     *
     * @param lastname Nuevo apellido.
     */
    fun setLastname(lastname: String) {
        _lastname.value = lastname
    }

    /**
     * Actualiza el correo electrónico en el formulario.
     *
     * @param email Nuevo correo electrónico.
     */
    fun setEmail(email: String) {
        _email.value = email
    }

    /**
     * Actualiza la contraseña en el formulario.
     *
     * @param password Nueva contraseña.
     */
    fun setPassword(password: String) {
        _password.value = password
    }

    /**
     * Valida que todos los campos del formulario cumplan con los requisitos.
     *
     * @return Mensaje de error si hay una validación fallida, o null si todo es válido.
     */
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

    /**
     * Ejecuta el proceso de registro validando los campos y llamando al caso de uso.
     * Actualiza los estados con el resultado o error de la operación.
     */
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
