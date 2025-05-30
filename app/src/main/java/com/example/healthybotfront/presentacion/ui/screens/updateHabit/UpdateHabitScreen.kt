package com.example.healthybotfront.presentacion.ui.screens.updateHabit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.viewmodel.HabitViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateHabitScreen(
    navController: NavController,
    habitId: Long,
    viewModel: HabitViewModel = koinViewModel()
) {
    LaunchedEffect(habitId) {
        viewModel.loadHabitById(habitId)
    }

    val selectedHabit by viewModel.selectedHabit
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(selectedHabit) {
        selectedHabit?.let {
            name = TextFieldValue(it.name)
            description = TextFieldValue(it.description)
        }
    }

    // 🎨 Colores estilo Login/Profile
    val backgroundColor = Color(0xFFF0E6F7) // lavanda pastel
    val darkColor = Color(0xFF3b0a58)       // morado oscuro
    val buttonPink = Color(0xFFF8D1D9)      // rosa pastel
    val buttonGreen = Color(0xFFC8E6C9)     // verde pastel suave
    val cardColor = Color.White

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Editar Hábito",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = darkColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = darkColor
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = cardColor)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
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
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = darkColor,
                            cursorColor = darkColor,
                            focusedLabelColor = darkColor
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonPink)
                        ) {
                            Text("Cancelar", color = darkColor)
                        }

                        Button(
                            onClick = {
                                viewModel.updateHabit(
                                    habitId = habitId,
                                    name = name.text,
                                    description = description.text,
                                    onSuccess = { navController.popBackStack() },
                                    onError = { /* Opcional: Mostrar error */ }
                                )
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonGreen)
                        ) {
                            Text("Guardar", color = darkColor)
                        }
                    }
                }
            }
        }
    }
}
