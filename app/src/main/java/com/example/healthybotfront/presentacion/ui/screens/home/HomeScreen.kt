package com.example.healthybotfront.presentacion.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    userId: Long,
    habitViewModel: HabitViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel()
) {

    val habits by habitViewModel.habits.collectAsState()
    val error by habitViewModel.errorMessage.collectAsState()
    val checkStates = remember { mutableStateMapOf<Long, Boolean>() }
    val user by profileViewModel.user.collectAsState()

    val currentDayName = remember {
        LocalDate.now()
            .dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale("es"))
            .replaceFirstChar { it.uppercaseChar() }
    }


    // Cargar hábitos solo una vez
    LaunchedEffect(userId) {
        habitViewModel.getHabits(userId)
        profileViewModel.loadUser(userId)
    }


    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = {
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
                IconButton(onClick = {
                    // Aquí puedes navegar a una pantalla para crear hábito
                    navController.navigate(Screen.CreateHabit.createRoute(userId))
                }) {
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
                text = "Hola, ${user?.name ?: "usuario"} 👋",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = currentDayName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(16.dp))

            if (error != null) {
                Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
            }

            habits.forEach { habit ->
                val isChecked = checkStates[habit.hashCode().toLong()] ?: false

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            checkStates[habit.hashCode().toLong()] = it
                            println("Hábito '${habit.name}' marcado como completado: $it")
                        }
                    )
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

        }
    }
}
