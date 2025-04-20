package com.example.healthybotfront.presentacion.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.navigation.Screen
import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    userId: Long,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val user by viewModel.user.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    // Si está cargando, muestra el indicador de carga
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Si hay un mensaje de error, muéstralo
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        // Si hay información del usuario, muéstrala
        user?.let { currentUser ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Perfil de usuario", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar información del usuario
                Text("Nombre: ${currentUser.name}")
                Text("Apellido: ${currentUser.lastName}")
                Text("Email: ${currentUser.email}")

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para eliminar cuenta
                OutlinedButton(onClick = {
                    viewModel.deleteUser(userId) {
                        navController.navigate(Screen.Login.route){
                            popUpTo("profile/$userId") { inclusive = true }
                        }
                    }
                }) {
                    Text("Eliminar cuenta", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
