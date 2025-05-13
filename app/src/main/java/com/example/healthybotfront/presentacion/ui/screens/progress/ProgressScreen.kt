package com.example.healthybotfront.presentacion.ui.screens.progress

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.ui.components.TopBarWithProfile
import com.example.healthybotfront.presentacion.viewmodel.GetProgressViewModel
import com.example.healthybotfront.presentacion.viewmodel.ProgressViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavController,
    userId: Long,
    progressViewModel: GetProgressViewModel = koinViewModel()
) {
    val habits by progressViewModel.habits.collectAsState()
    val progressList by progressViewModel.progressList.collectAsState()
    val error by progressViewModel.error.collectAsState()
    val percentage by progressViewModel.combinedMonthlyProgress.collectAsState()

    LaunchedEffect(userId) {
        progressViewModel.loadUserProgress(userId)
    }

    Scaffold(
        topBar = {
            TopBarWithProfile(
                navController = navController,
                userId = userId,
                title = "Progreso mensual"
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

            if (habits.isNotEmpty() && progressList.isNotEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Progreso mensual combinado:", fontWeight = FontWeight.Bold)
                        Text("Completado: ${(percentage * 100).toInt()}%")
                        LinearProgressIndicator(
                            progress = percentage,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }
            }

            if (error != null) {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
