package com.example.healthybotfront.presentacion.ui.screens.habits

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.data.source.remote.dto.GoalDto
import com.example.healthybotfront.data.source.remote.dto.HabitDto
import com.example.healthybotfront.presentacion.viewmodel.GoalViewModel
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHabitScreen(
    navController: NavController,
    userId: Long,
    habitViewModel: HabitViewModel = koinViewModel(),
    goalViewModel: GoalViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val errorMessage by habitViewModel.errorMessage.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Meta (Goal) states
    var objective by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    // Frecuencia con Dropdown
    val frequencyOptions = listOf("DAILY", "WEEKLY", "MONTHLY")
    var selectedFrequency by remember { mutableStateOf(frequencyOptions[0]) }
    var expanded by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Hábito + Meta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------- HÁBITO ----------
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del hábito") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- META ----------
            Text("Meta asociada", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = objective,
                onValueChange = { objective = it },
                label = { Text("Objetivo de la meta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Frecuencia")
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedFrequency,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Frecuencia") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    frequencyOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedFrequency = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("Fecha fin (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (showError || errorMessage != null) {
                Text(
                    text = errorMessage ?: "Completa todos los campos correctamente.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (name.isNotBlank() && description.isNotBlank() &&
                        objective.isNotBlank() && endDate.isNotBlank()
                    ) {
                        val habit = HabitDto(
                            name = name,
                            description = description,
                            isCustom = true,
                            userId = userId
                        )

                        habitViewModel.createHabit(habit) { createdHabit ->
                            println("Habit creado con ID: ${createdHabit.habitId}")

                            // Asegúrate de que `createdHabit.habitId` esté disponible y sea correcto
                            val goal = GoalDto(
                                user_id = userId,
                                habit_id = createdHabit.habitId!!, // Ahora usas `habitId` que se espera
                                objective = objective,
                                frequency = selectedFrequency,
                                startDate = LocalDate.now().toString(),
                                endDate = endDate
                            )

                            goalViewModel.create(goal)

                            Toast.makeText(context, "Hábito y meta creados correctamente", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar hábito y meta")
            }
        }
    }
}
