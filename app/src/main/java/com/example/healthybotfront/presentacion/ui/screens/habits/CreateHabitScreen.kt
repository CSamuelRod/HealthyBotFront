package com.example.healthybotfront.presentacion.ui.screens.habits

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
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

    val displayFrequencyOptions = listOf("Diario", "Semanal", "Mensual")
    val frequencyMap = mapOf(
        "Diario" to "DAILY",
        "Semanal" to "WEEKLY",
        "Mensual" to "MONTHLY"
    )
    var selectedDisplayFrequency by remember { mutableStateOf(displayFrequencyOptions[0]) }
    var expanded by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        LaunchedEffect(Unit) {
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    endDate = dateFormatter.format(calendar.time)
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                setOnCancelListener { showDatePicker = false }
            }.show()
        }
    }

    // 🎨 Colores pastel coherentes
    val backgroundColor = Color(0xFFF0E6F7)
    val cardColor = Color.White
    val darkColor = Color(0xFF3b0a58)
    val buttonColor = Color(0xFFC8E6C9)
    val errorColor = MaterialTheme.colorScheme.error

    Scaffold(
        containerColor = backgroundColor,
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
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Nuevo Hábito", style = MaterialTheme.typography.headlineSmall, color = darkColor)

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre del hábito") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = darkColor,
                            cursorColor = darkColor,
                            focusedLabelColor = darkColor
                        )
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = darkColor,
                            cursorColor = darkColor,
                            focusedLabelColor = darkColor
                        )
                    )

                    Divider()

                    Text("Meta Asociada", style = MaterialTheme.typography.titleMedium, color = darkColor)

                    OutlinedTextField(
                        value = objective,
                        onValueChange = { objective = it },
                        label = { Text("Objetivo de la meta") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = darkColor,
                            cursorColor = darkColor,
                            focusedLabelColor = darkColor
                        )
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedDisplayFrequency,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Frecuencia") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = darkColor,
                                cursorColor = darkColor,
                                focusedLabelColor = darkColor
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            displayFrequencyOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        selectedDisplayFrequency = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = endDate,
                        onValueChange = {},
                        label = { Text("Fecha fin (YYYY-MM-DD)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        readOnly = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = darkColor,
                            cursorColor = darkColor,
                            focusedLabelColor = darkColor
                        )
                    )

                    if (showError || errorMessage != null) {
                        Text(
                            text = errorMessage ?: "Completa todos los campos correctamente.",
                            color = errorColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (name.isNotBlank() && description.isNotBlank() &&
                                objective.isNotBlank() && endDate.isNotBlank()
                            ) {
                                val habit = HabitDto(
                                    name = name,
                                    description = description,
                                    userId = userId
                                )
                                habitViewModel.createHabit(habit) { createdHabit ->
                                    val goal = GoalDto(
                                        goalId = null,
                                        habit_id = createdHabit.habitId!!,
                                        objective = objective,
                                        frequency = frequencyMap[selectedDisplayFrequency] ?: "DAILY",
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
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                    ) {
                        Text("Guardar hábito y meta", color = darkColor)
                    }
                }
            }
        }
    }
}
