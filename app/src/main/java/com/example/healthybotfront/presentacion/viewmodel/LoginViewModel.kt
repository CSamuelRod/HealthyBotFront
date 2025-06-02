package com.example.healthybotfront.presentacion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthybotfront.domain.usecase.AuthUseCases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de gestionar la lógica de autenticación y login del usuario.
 *
 * @property loginUseCase Caso de uso para realizar el login.
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    /** Estado del correo electrónico ingresado por el usuario */
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    /** Estado de la contraseña ingresada por el usuario */
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    /** ID del usuario autenticado, null si no hay usuario logueado */
    private val _userId = MutableStateFlow<Long?>(null)
    val userId: StateFlow<Long?> = _userId

    /** Mensaje de error para mostrar en la interfaz */
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    /**
     * Actualiza el valor del correo electrónico ingresado.
     *
     * @param email Correo electrónico ingresado por el usuario.
     */
    fun setEmail(email: String) {
        _email.value = email
    }

    /**
     * Actualiza el valor de la contraseña ingresada.
     *
     * @param password Contraseña ingresada por el usuario.
     */
    fun setPassword(password: String) {
        _password.value = password
    }

    /**
     * Ejecuta el proceso de login utilizando el correo y contraseña proporcionados.
     *
     * Realiza validaciones locales antes de llamar al caso de uso.
     * Actualiza los estados [_userId] y [_error] según el resultado.
     */
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

    /**
     * Valida que un correo electrónico tenga un formato correcto.
     *
     * @param email Correo electrónico a validar.
     * @return `true` si el correo tiene un formato válido, `false` en caso contrario.
     */
    private fun isValidEmail(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return regex.matches(email)
    }
}
