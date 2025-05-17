package com.example.healthybotfront.presentacion.ui.screens.habits

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.data.source.remote.dto.GoalDto
import com.example.healthybotfront.data.source.remote.dto.HabitDto
import com.example.healthybotfront.presentacion.ui.components.TopBarWithProfile
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

    var objective by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    val frequencyOptions = listOf("DAILY", "WEEKLY", "MONTHLY")
    var selectedFrequency by remember { mutableStateOf(frequencyOptions[0]) }
    var expanded by remember { mutableStateOf(false) }

    var showError by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color(0xFFE8F5E9), // Fondo pastel
        topBar = {
            TopBarWithProfile(
                navController = navController,
                userId = userId,
                title = "Nuevo Hábito + Meta"
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // -------- Hábito --------
                    Text("Nuevo Hábito", style = MaterialTheme.typography.headlineSmall)

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre del hábito") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // -------- Meta --------
                    Text("Meta Asociada", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = objective,
                        onValueChange = { objective = it },
                        label = { Text("Objetivo de la meta") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Frecuencia con Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedFrequency,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Frecuencia") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
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

                    OutlinedTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = { Text("Fecha fin (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (showError || errorMessage != null) {
                        Text(
                            text = errorMessage ?: "Completa todos los campos correctamente.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() && description.isNotBlank()
                                && objective.isNotBlank() && endDate.isNotBlank()
                            ) {
                                val habit = HabitDto(
                                    name = name,
                                    description = description,
                                    isCustom = true,
                                    userId = userId
                                )

                                habitViewModel.createHabit(habit) { createdHabit ->
                                    val goal = GoalDto(
                                        goalId = null,
                                        user_id = userId,
                                        habit_id = createdHabit.habitId!!,
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81C784))
                    ) {
                        Text("Guardar hábito y meta", color = Color.White)
                    }
                }
            }
        }
    }
}
