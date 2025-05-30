package com.example.healthybotfront.presentacion.ui.screens.notificationTimePickerScreen
// presentation/ui/screens/

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.healthybotfront.data.receiver.NotificationReceiver
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Configura tu recordatorio",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = "Hora actual: %02d:%02d".format(hour, minute),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(
                        onClick = { timePickerDialog.show() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Seleccionar hora")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(context, NotificationReceiver::class.java)
                            context.sendBroadcast(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Probar Notificación")
                    }
                    Button(
                        onClick = {
                            viewModel.notificationTime.value.let { time ->
                                Log.d("NotificationTimePicker", "Hora registrada: ${time.hour}, Minuto registrado: ${time.minute}")
                                viewModel.setNotificationTime(context, time.hour, time.minute)
                            }
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}
