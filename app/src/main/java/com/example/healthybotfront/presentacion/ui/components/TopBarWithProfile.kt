package com.example.healthybotfront.presentacion.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.healthybotfront.presentacion.navigation.Screen

/**
 * Barra superior con título centrado, botón de retroceso a la izquierda (opcional)
 * y botón de perfil a la derecha que navega a la pantalla de perfil del usuario.
 *
 * @param navController Controlador de navegación para manejar las acciones de los botones.
 * @param userId Id del usuario usado para construir la ruta del perfil.
 * @param title Texto que se muestra en el centro de la barra.
 * @param showBackButton Indica si se muestra el botón de retroceso (por defecto es true).
 */
@Composable
fun TopBarWithProfile(
    navController: NavController,
    userId: Long,
    title: String,
    showBackButton: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Botón de retroceso (izquierda)
        if (showBackButton) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás"
                )
            }
        }

        // Título centrado
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )

        // Botón de perfil (derecha)
        IconButton(
            onClick = {
                navController.navigate(Screen.Profile.createRoute(userId))
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Perfil"
            )
        }
    }
}
