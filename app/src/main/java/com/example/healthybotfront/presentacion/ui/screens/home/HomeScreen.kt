package com.example.healthybotfront.presentacion.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.healthybotfront.presentacion.viewmodel.ProgressViewModel
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
    val checkStates = remember { mutableStateMapOf<Long, Boolean>() }
    val notesMap = remember { mutableStateMapOf<Long, String>() }
    val showControlsMap = remember { mutableStateMapOf<Long, Boolean>() }
    val user by profileViewModel.user.collectAsState()

    val currentDayName = remember {
        LocalDate.now()
            .dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale("es"))
            .replaceFirstChar { it.uppercaseChar() }
    }

    LaunchedEffect(userId) {
        habitViewModel.getHabits(userId)
        profileViewModel.loadUser(userId)
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
        }
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
                text = currentDayName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (error != null) {
                Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
            }
            habits.forEach { habit ->
                val habitId = habit.habitId!!
                val isChecked = checkStates[habitId] ?: false
                val note = notesMap[habitId] ?: ""
                val showControls = showControlsMap[habitId] ?: false
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
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

                                if (!checked) {
                                    println("se ha desmarcado el habit ${habit.habitId}")
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
                                .padding(top = 4.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                        ) {
                            TextButton(onClick = {
                                progressViewModel.saveProgress(
                                    habitId = habitId,
                                    completed = true,
                                    notes = null,
                                    onError = { println(it) }
                                )
                                showControlsMap[habitId] = false
                                notesMap[habitId] = ""
                            }) {
                                Text("Cancelar")
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                progressViewModel.saveProgress(
                                    habitId = habitId,
                                    completed = true,
                                    notes = note.takeIf { it.isNotBlank() },
                                    onError = { println(it) }
                                )
                                showControlsMap[habitId] = false
                                notesMap[habitId] = ""
                            }) {
                                Text("Guardar")
                            }
                        }
                    }
                }
            }
        }
    }
}
