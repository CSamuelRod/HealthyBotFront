package com.example.healthybotfront.presentacion.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import com.example.healthybotfront.presentacion.viewmodel.GoalViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverallScreen(
    navController: NavController,
    userId: Long,
    habitViewModel: HabitViewModel = koinViewModel(),
    goalViewModel: GoalViewModel = koinViewModel()
) {
    val habits by habitViewModel.habits.collectAsState()
    //val goals by goalViewModel.goals.collectAsState()

    LaunchedEffect(userId) {
        habitViewModel.getHabits(userId)
        //goalViewModel.getGoals(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen General") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("¡Hola! Aquí tienes tu progreso", style = MaterialTheme.typography.headlineSmall)

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Hábitos creados:", fontWeight = FontWeight.Bold)
                    Text("${habits.size} hábitos")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Metas activas:", fontWeight = FontWeight.Bold)
                    Text(" metas")
                }
            }
/*
            if (habits.isNotEmpty() || goals.isNotEmpty()) {
                Text(
                    "¡Sigue así! Estás construyendo una gran rutina.",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                Text(
                    "Aún no tienes hábitos o metas creadas. ¡Empieza hoy!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }*/
        }
    }
}
