package com.example.healthybotfront.presentacion.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.R
import com.example.healthybotfront.presentacion.navigation.Screen
import com.example.healthybotfront.presentacion.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

// Colores basados en los códigos proporcionados con tonos pastel y planos
private val DarkColor = Color(0xFF3b0a58)         // oscuro base
private val LightColor = Color(0xFF5a3a84)        // claro base
private val AccentColor = Color(0xFFff3b5f)       // color acento (letra app)
private val BackgroundPastel = Color(0xFFF0E6F7)  // pastel suave derivado de oscuro
private val ButtonPastel = Color(0xFFF8D1D9)      // pastel suave derivado de acento

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val userId by viewModel.userId.collectAsState()
    val error by viewModel.error.collectAsState()
    val isFormValid = email.isNotBlank() && password.isNotBlank()

    LaunchedEffect(userId) {
        if (userId != null && userId != -1L) {
            println("Exitoso login con id : $userId")
            navController.navigate(Screen.Home.createRoute(userId!!))
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundPastel
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.bienvenido),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 12.dp)
                    )
// Cambia el color del texto del título:
                    Text(
                        text = "Bienvenido a HealthyBot",
                        style = MaterialTheme.typography.headlineMedium,
                        color = DarkColor,     // <-- Aquí usas AccentColor
                        textAlign = TextAlign.Center
                    )

// Cambia el color de los iconos en los TextFields:
                    OutlinedTextField(
                        value = email,
                        onValueChange = viewModel::setEmail,
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = AccentColor) },  // <-- AccentColor
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentColor,
                            unfocusedBorderColor = LightColor,
                            focusedLabelColor = AccentColor,
                            cursorColor = AccentColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = viewModel::setPassword,
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = AccentColor) },  // <-- AccentColor
                        visualTransformation = PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentColor,
                            unfocusedBorderColor = LightColor,
                            focusedLabelColor = AccentColor,
                            cursorColor = AccentColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )


                    TextButton(
                        onClick = { navController.navigate(Screen.ForgotPassword.route) },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 4.dp)
                    ) {
                        Text("¿Olvidaste tu contraseña?", color = DarkColor)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ButtonPastel
                        )
                    ) {
                        Text("Iniciar sesión", color = DarkColor)
                    }

                    if (error.isNotBlank()) {
                        Text(
                            text = error,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { navController.navigate(Screen.Register.route) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = LightColor.copy(alpha = 0.15f),
                            contentColor = DarkColor
                        )
                    ) {
                        Text("Registrarse")
                    }
                }
            }
        }
    }
}
