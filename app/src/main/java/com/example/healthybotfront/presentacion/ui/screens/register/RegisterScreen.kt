package com.example.healthybotfront.presentation.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.R
import com.example.healthybotfront.presentacion.navigation.Screen
import com.example.healthybotfront.presentacion.viewmodel.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

private val DarkColor = Color(0xFF3b0a58)
private val LightColor = Color(0xFF5a3a84)
private val AccentColor = Color(0xFFff3b5f)
private val BackgroundPastel = Color(0xFFF0E6F7)
private val ButtonPastel = Color(0xFFF8D1D9)

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val registerResult by viewModel.registerState.collectAsState()
    val isRegistrationSuccessful by viewModel.isRegistrationSuccessful.collectAsState()

    var showErrorDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(name) { viewModel.setName(name) }
    LaunchedEffect(lastname) { viewModel.setLastname(lastname) }
    LaunchedEffect(email) { viewModel.setEmail(email) }
    LaunchedEffect(password) { viewModel.setPassword(password) }

    LaunchedEffect(isRegistrationSuccessful) {
        if (isRegistrationSuccessful) {
            Toast.makeText(context, registerResult, Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Login.route)
        }
    }

    LaunchedEffect(registerResult) {
        if (registerResult.startsWith("Error")) {
            showErrorDialog = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundPastel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.usuario),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Crea tu cuenta",
                        style = MaterialTheme.typography.headlineSmall,
                        color = DarkColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        singleLine = true,
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
                        value = lastname,
                        onValueChange = { lastname = it },
                        label = { Text("Apellido") },
                        singleLine = true,
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
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = AccentColor) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        singleLine = true,
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
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = AccentColor) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AccentColor,
                            unfocusedBorderColor = LightColor,
                            focusedLabelColor = AccentColor,
                            cursorColor = AccentColor
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = LightColor.copy(alpha = 0.15f),
                                contentColor = DarkColor
                            )
                        ) {
                            Text("Cancelar")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = { viewModel.register() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = ButtonPastel)
                        ) {
                            Text("Guardar", color = DarkColor)
                        }
                    }

                    if (showErrorDialog) {
                        AlertDialog(
                            onDismissRequest = { showErrorDialog = false },
                            confirmButton = {
                                TextButton(onClick = { showErrorDialog = false }) {
                                    Text("Aceptar", color = AccentColor)
                                }
                            },
                            title = { Text("Error de Registro", color = DarkColor) },
                            text = { Text(registerResult, color = DarkColor) },
                            containerColor = Color.White
                        )
                    }
                }
            }
        }
    }
}
