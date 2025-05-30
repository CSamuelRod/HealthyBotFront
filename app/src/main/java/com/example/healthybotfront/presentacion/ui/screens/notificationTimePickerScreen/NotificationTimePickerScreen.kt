package com.example.healthybotfront.presentacion.ui.screens.notificationTimePickerScreen

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.viewmodel.NotificationViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTimePickerScreen(
    navController: NavController,
    viewModel: NotificationViewModel = koinViewModel()
) {
    val notificationTime by viewModel.notificationTime.collectAsState()
    val (hour, minute) = notificationTime
    val context = LocalContext.current

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                viewModel.setNotificationTime(context, selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )
    }

    // 🎨 Colores pastel
    val backgroundColor = Color(0xFFECEFF1)
    val buttonGreen = Color(0xFFC8E6C9)
    val buttonBlue = Color(0xFFB3E5FC)
    val darkText = Color(0xFF3b0a58)

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notificaciones",
                        style = MaterialTheme.typography.titleLarge,
                        color = darkText
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = darkText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Configura la hora en la que deseas recibir recordatorios",
                style = MaterialTheme.typography.bodyLarge,
                color = darkText
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Hora actual: %02d:%02d".format(hour, minute),
                style = MaterialTheme.typography.headlineSmall,
                color = darkText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { timePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonGreen)
            ) {
                Text("Seleccionar hora", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonBlue)
            ) {
                Text("Confirmar", color = Color.Black)
            }
        }
    }
}
