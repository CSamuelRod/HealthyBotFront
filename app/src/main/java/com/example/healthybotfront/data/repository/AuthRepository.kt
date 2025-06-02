package com.example.healthybotfront.data.repository

import com.example.healthybotfront.data.source.remote.api.AuthApi
import com.example.healthybotfront.data.source.remote.dto.LoginRequest
import com.example.healthybotfront.data.source.remote.dto.RegisterRequest

/**
 * Repositorio encargado de manejar las operaciones de autenticación.
 *
 * Usa la API remota [AuthApi] para registrar usuarios y realizar login.
 *
 * @property authApi instancia de la interfaz API para autenticación
 */
class AuthRepository(
    private val authApi: AuthApi
) {

    /**
     * Registra un nuevo usuario usando los datos proporcionados.
     *
     * Envía una solicitud a la API remota con la información del usuario.
     * Retorna el mensaje de respuesta o un mensaje de error en caso de fallo.
     *
     * @param name Nombre del usuario
     * @param lastname Apellido del usuario
     * @param email Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @return Mensaje de éxito o error como String
     */
    suspend fun register(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): String {
        return try {
            val response = authApi.register(RegisterRequest(name, lastname, email, password))
            response.message
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message ?: "Error inesperado"}"
        }
    }

    /**
     * Realiza el login del usuario con email y contraseña.
     *
     * Envía las credenciales a la API remota y retorna un par con
     * el mensaje de la respuesta y el ID del usuario en caso de éxito.
     *
     * En caso de error 401 (no autorizado), se maneja el mensaje personalizado.
     * Otros errores retornan un mensaje genérico.
     *
     * @param email Correo electrónico del usuario
     * @param password Contraseña del usuario
     * @return Par que contiene el mensaje y el ID del usuario (o null si falla)
     */
    suspend fun login(email: String, password: String): Pair<String, Long?> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            Pair(response.message, response.userId)
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 401) {
                // Maneja error de autenticación específicamente
                val errorBody = e.response()?.errorBody()?.string()
                val message = if (errorBody != null && errorBody.contains("Credenciales inválidas")) {
                    "Credenciales inválidas"
                } else {
                    "No autorizado"
                }
                Pair(message, -1L)
            } else {
                e.printStackTrace()
                Pair("Error: ${e.message ?: "Error inesperado"}", null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Pair("Error: ${e.message ?: "Error inesperado"}", null)
        }
    }
}
