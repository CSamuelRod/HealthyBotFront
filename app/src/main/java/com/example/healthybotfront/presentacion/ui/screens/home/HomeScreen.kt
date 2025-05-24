package com.example.healthybotfront.presentacion.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.navigation.Screen
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProfileViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProgressViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    userId: Long,
    habitViewModel: HabitViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel(),
    progressViewModel: ProgressViewModel = koinViewModel()
) {
    val habits by habitViewModel.habits.collectAsState()
    val error by habitViewModel.errorMessage.collectAsState()
    val user by profileViewModel.user.collectAsState()

    val currentDate = LocalDate.now()
    val currentDayName = currentDate
        .dayOfWeek
        .getDisplayName(TextStyle.FULL, Locale("es"))
        .replaceFirstChar { it.uppercaseChar() }
    val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM"))

    // Estados para checkbox, notas y controles
    val checkStates = remember { mutableStateMapOf<Long, Boolean>() }
    val notesMap = remember { mutableStateMapOf<Long, String>() }
    val showControlsMap = remember { mutableStateMapOf<Long, Boolean>() }
    // Guardamos progressId para cada hábito para eliminar
    val progressIdMap = remember { mutableStateMapOf<Long, Long>() }

    // Progreso diario desde ViewModel
    val dailyProgress by progressViewModel.dailyProgress.collectAsState()

    // Cargar datos al iniciar pantalla
    LaunchedEffect(userId) {
        habitViewModel.getHabits(userId)
        profileViewModel.loadUser(userId)
        progressViewModel.loadDailyProgress(userId, currentDate)
    }

    // Sincronizar estado de checkboxes y progressId con progreso cargado
    LaunchedEffect(dailyProgress) {
        dailyProgress.forEach { (habitId, progressDto) ->
            checkStates[habitId] = progressDto.completed
            // Asumiendo que ProgressDto tiene progressId
            progressDto.progressId?.let { progressIdMap[habitId] = it }
        }
    }

    Scaffold(
        topBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
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
                    navController.navigate(Screen.CreateHabit.createRoute(userId))
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar"
                    )
                }
                TextButton(onClick = {
                    navController.navigate(Screen.Progress.createRoute(userId))
                }) {
                    Text("Progreso")
                }
            }
        },
        containerColor = Color(0xFFF1F8E9) // Fondo pastel verde claro
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                text = "$currentDayName - $formattedDate",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (error != null) {
                Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
            }

            habits.forEach { habit ->
                val habitId = habit.habitId ?: return@forEach
                val isChecked = checkStates[habitId] ?: false
                val note = notesMap[habitId] ?: ""
                val showControls = showControlsMap[habitId] ?: false

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .clickable {
                            showControlsMap[habitId] = !(showControlsMap[habitId] ?: false)
                        }
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = habit.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = habit.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                checkStates[habitId] = checked
                                showControlsMap[habitId] = checked

                                if (checked) {
                                    // Crear o actualizar progreso
                                    progressViewModel.saveProgress(
                                        habitId = habitId,
                                        completed = true,
                                        notes = notesMap[habitId]?.takeIf { it.isNotBlank() },
                                        onError = { println("Error guardando progreso: $it") }
                                    )
                                } else {
                                    // Eliminar progreso si existe progressId
                                    progressIdMap[habitId]?.let { progressId ->
                                        progressViewModel.deleteProgress(progressId)
                                        progressIdMap.remove(habitId)
                                    }
                                    notesMap[habitId] = ""
                                }
                            }
                        )
                    }

                    if (showControls) {
                        OutlinedTextField(
                            value = note,
                            onValueChange = { notesMap[habitId] = it },
                            label = { Text("Nota (opcional)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Button(
                                onClick = {
                                    habitViewModel.deleteHabit(habitId) {
                                        habitViewModel.getHabits(userId)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD6D6))
                            ) {
                                Text("Eliminar")
                            }

                            Button(
                                onClick = {
                                    // navController.navigate(Screen.EditHabit.createRoute(userId, habitId))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCE5FF))
                            ) {
                                Text("Modificar")
                            }

                            Button(
                                onClick = {
                                    progressViewModel.saveProgress(
                                        habitId = habitId,
                                        completed = true,
                                        notes = note.takeIf { it.isNotBlank() },
                                        onError = { println(it) }
                                    )
                                    showControlsMap[habitId] = false
                                    notesMap[habitId] = ""
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD5F5E3))
                            ) {
                                Text("Guardar")
                            }
                        }
                    }
                }
            }
        }
    }
}
