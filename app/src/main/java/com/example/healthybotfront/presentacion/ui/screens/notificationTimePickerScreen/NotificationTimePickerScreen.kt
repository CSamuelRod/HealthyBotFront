package com.example.healthybotfront.presentacion.ui.screens.notificationTimePickerScreen

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.viewmodel.NotificationViewModel
import org.koin.androidx.compose.koinViewModel



@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
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
                viewModel.setNotificationTime(context, selectedHour, selectedMinute) // Pasa context aquí
            },
            hour,
            minute,
            true
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Configurar hora de notificación") }
            )
        },
        containerColor = Color(0xFFF1F8E9)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Hora actual: %02d:%02d".format(hour, minute),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { timePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8E6C9)),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Seleccionar hora", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCCE5FF)),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Volver")
            }
        }
    }
}
