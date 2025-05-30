package com.example.healthybotfront.presentacion.ui.screens.forgotPasswordScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.viewmodel.ForgotPasswordViewModel
import org.koin.androidx.compose.koinViewModel

private val DarkColor = Color(0xFF3b0a58)
private val LightColor = Color(0xFF5a3a84)
private val AccentColor = Color(0xFFff3b5f)
private val BackgroundPastel = Color(0xFFF0E6F7)
private val ButtonPastel = Color(0xFFF8D1D9)

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    val email by viewModel.email.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val message by viewModel.message.collectAsState()

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
                    Text(
                        text = "Recuperar contraseña",
                        style = MaterialTheme.typography.headlineMedium,
                        color = DarkColor,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = viewModel::setEmail,
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = AccentColor) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentColor,
                            unfocusedBorderColor = LightColor,
                            focusedLabelColor = AccentColor,
                            cursorColor = AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = viewModel::setNewPassword,
                        label = { Text("Nueva contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = AccentColor) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentColor,
                            unfocusedBorderColor = LightColor,
                            focusedLabelColor = AccentColor,
                            cursorColor = AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.resetPassword() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonPastel)
                    ) {
                        Text("Restablecer contraseña", color = DarkColor)
                    }

                    if (message.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = message,
                            color = if (message.startsWith("Error")) Color.Red else Color.Green,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TextButton(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.textButtonColors(contentColor = AccentColor)
                    ) {
                        Text("Volver a iniciar sesión")
                    }
                }
            }
        }
    }
}
