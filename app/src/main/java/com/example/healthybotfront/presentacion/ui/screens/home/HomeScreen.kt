package com.example.healthybotfront.presentacion.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavController,
    userId: Long
) {
    // Guardamos el userId en una variable de estado (opcional si solo lo mostramos)
    val currentUserId = remember { userId }

    var tasks by remember {
        mutableStateOf(
            listOf(
                Task("Hacer ejercicio", false),
                Task("Tomar agua", true),
                Task("Leer 10 minutos", false)
            )
        )
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Botón de perfil en la esquina superior derecha
                IconButton(
                    onClick = {
                        // Navegar a la pantalla de perfil con el userId
                        navController.navigate(Screen.Profile.createRoute(userId))
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* Acción para añadir */ }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar"
                    )
                }
                TextButton(onClick = { /* Acción para logros */ }) {
                    Text("Logros")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido, usuario ID: $currentUserId",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Lunes",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Aquí podrías mapear tareas o cualquier otra info
        }
    }
}

data class Task(val name: String, val done: Boolean)
