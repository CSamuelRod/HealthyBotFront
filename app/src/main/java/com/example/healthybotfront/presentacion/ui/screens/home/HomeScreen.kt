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
import androidx.compose.material.icons.filled.ArrowBack
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

private val DarkColor = Color(0xFF3b0a58)         // oscuro base (igual que login)
private val LightColor = Color(0xFF5a3a84)        // claro base (igual que login)
private val AccentColor = Color(0xFFff3b5f)       // color acento (igual que login)
private val BackgroundPastel = Color(0xFFF0E6F7)  // fondo pastel suave (igual que login)
private val ButtonPastel = Color(0xFFF8D1D9)      // botones pastel (igual que login)

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
    val currentDayName = currentDate.dayOfWeek
        .getDisplayName(TextStyle.FULL, Locale("es"))
        .replaceFirstChar { it.uppercaseChar() }
    val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd/MM"))

    val checkStates = remember { mutableStateMapOf<Long, Boolean>() }
    val notesMap = remember { mutableStateMapOf<Long, String>() }
    val showControlsMap = remember { mutableStateMapOf<Long, Boolean>() }
    val progressIdMap = remember { mutableStateMapOf<Long, Long>() }

    val dailyProgress by progressViewModel.dailyProgress.collectAsState()

    LaunchedEffect(userId) {
        habitViewModel.getHabits(userId)
        profileViewModel.loadUser(userId)
        progressViewModel.loadDailyProgress(userId, currentDate)
    }

    LaunchedEffect(dailyProgress) {
        dailyProgress.forEach { (habitId, progressDto) ->
            checkStates[habitId] = progressDto.completed
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
                // Botón de Logout (izquierda)
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Login.route)
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Cerrar sesión",
                        modifier = Modifier.size(28.dp),
                        tint = DarkColor
                    )
                }

                // Botón de Perfil (derecha)
                IconButton(
                    onClick = {
                        navController.navigate(Screen.Profile.createRoute(userId))
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(32.dp),
                        tint = DarkColor
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
                        contentDescription = "Agregar",
                        tint = DarkColor
                    )
                }
                TextButton(onClick = {
                    navController.navigate(Screen.Progress.createRoute(userId))
                }) {
                    Text("Progreso", color = DarkColor)
                }
            }
        },
        containerColor = BackgroundPastel
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackgroundPastel)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hola, ${user?.name ?: "usuario"} 👋",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkColor,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$currentDayName - $formattedDate",
                style = MaterialTheme.typography.titleLarge,
                color = DarkColor,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (error != null) {
                Text(text = "Error: $error", color = Color.Red)
            }

            habits.forEach { habit ->
                val habitId = habit.habitId ?: return@forEach
                val isChecked = checkStates[habitId] ?: false
                val note = notesMap[habitId] ?: ""
                val showControls = showControlsMap[habitId] ?: false
                val hasSavedProgress = progressIdMap[habitId] != null
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
                            Text(text = habit.name, style = MaterialTheme.typography.bodyLarge, color = DarkColor)
                            Text(
                                text = habit.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = LightColor
                            )
                        }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                checkStates[habitId] = checked
                                showControlsMap[habitId] = checked

                                if (!checked) {
                                    progressIdMap[habitId]?.let { progressId ->
                                        progressViewModel.deleteProgress(progressId)
                                        progressIdMap.remove(habitId)
                                    }
                                    notesMap[habitId] = ""
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = AccentColor,
                                uncheckedColor = LightColor
                            )
                        )
                    }

                    if (showControls) {
                        OutlinedTextField(
                            value = note,
                            enabled = isChecked,
                            onValueChange = { notesMap[habitId] = it },
                            label = { Text("Nota (opcional)", color = DarkColor) },

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
                                colors = ButtonDefaults.buttonColors(containerColor = ButtonPastel)
                            ) {
                                Text("Eliminar", color = DarkColor)
                            }

                            Button(
                                onClick = {
                                    navController.navigate(Screen.UpdateHabit.createRoute(habitId))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ButtonPastel)
                            ) {
                                Text("Modificar", color = DarkColor)
                            }

                            Button(
                                onClick = {
                                    progressViewModel.saveProgress(
                                        habitId = habitId,
                                        completed = true,
                                        notes = note.takeIf { it.isNotBlank() },
                                        onError = { println("Error guardando progreso: $it") }
                                    )
                                    showControlsMap[habitId] = false
                                    checkStates[habitId] = true
                                },
                                enabled = isChecked && !hasSavedProgress,  // Sólo habilitado si está checked y NO hay progreso guardado
                                colors = ButtonDefaults.buttonColors(containerColor = ButtonPastel)
                            ) {
                                Text("Guardar", color = DarkColor)
                            }

                        }
                    }
                }
            }
        }
    }
}
