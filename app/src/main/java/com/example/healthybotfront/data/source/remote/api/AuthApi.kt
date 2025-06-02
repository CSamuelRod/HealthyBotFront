package com.example.healthybotfront.data.source.remote.api

import com.example.healthybotfront.data.source.remote.dto.LoginRequest
import com.example.healthybotfront.data.source.remote.dto.LoginResponse
import com.example.healthybotfront.data.source.remote.dto.RegisterRequest
import com.example.healthybotfront.data.source.remote.dto.RegisterResponse
import retrofit2.http.*

/**
 * Interfaz para las operaciones de autenticación con la API.
 * Permite registrar usuarios y realizar login.
 */
interface AuthApi {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param registerRequest DTO con los datos necesarios para el registro
     * @return DTO con la respuesta del registro, que puede incluir información del usuario creado
     */
    @POST("/api/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    /**
     * Realiza el login de un usuario con sus credenciales.
     *
     * @param loginRequest DTO con las credenciales de acceso (usuario y contraseña)
     * @return DTO con la respuesta del login, que puede incluir token de autenticación y datos del usuario
     */
    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
}
