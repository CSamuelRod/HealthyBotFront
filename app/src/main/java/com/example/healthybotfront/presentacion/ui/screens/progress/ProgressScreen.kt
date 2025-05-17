package com.example.healthybotfront.presentacion.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.ui.components.TopBarWithProfile
import com.example.healthybotfront.presentacion.viewmodel.GetProgressPercentageViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    navController: NavController,
    userId: Long,
    progressViewModel: GetProgressPercentageViewModel = koinViewModel()
) {
    val progressList by progressViewModel.percentage.collectAsState()
    val error by progressViewModel.error.collectAsState()

    LaunchedEffect(userId) {
        progressViewModel.loadPercentage(userId)
    }

    Scaffold(
        topBar = {
            TopBarWithProfile(
                navController = navController,
                userId = userId,
                title = "Progreso mensual"
            )
        },
        containerColor = Color(0xFFF1F8E9) // Fondo pastel verde claro
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Tu progreso por hábito",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error
                )
            } else if (progressList.isEmpty()) {
                Text("No hay progreso disponible.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(progressList) { progress ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    text = progress.habitName ?: "Nombre no disponible",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Completado: ${progress.progressPercentage.roundToInt()}%",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 4.dp)
                                )

                                LinearProgressIndicator(
                                    progress = (progress.progressPercentage / 100).toFloat().coerceIn(0f, 1f),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    color = Color(0xFF81C784) // verde suave
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
