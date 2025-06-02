package com.example.healthybotfront.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.healthybotfront.presentacion.ui.screens.forgotPasswordScreen.ForgotPasswordScreen
import com.example.healthybotfront.presentacion.ui.screens.habits.CreateHabitScreen
import com.example.healthybotfront.presentacion.ui.screens.login.LoginScreen
import com.example.healthybotfront.presentacion.ui.screens.home.HomeScreen
import com.example.healthybotfront.presentacion.ui.screens.notificationTimePickerScreen.NotificationTimePickerScreen
import com.example.healthybotfront.presentacion.ui.screens.profile.ProfileScreen
import com.example.healthybotfront.presentacion.ui.screens.progress.ProgressScreen
import com.example.healthybotfront.presentacion.ui.screens.updateHabit.UpdateHabitScreen
import com.example.healthybotfront.presentacion.ui.screens.updateUserProfile.UpdateUserScreen
import com.example.healthybotfront.presentation.register.RegisterScreen

/**
 * Composable que define la navegación principal de la aplicación.
 * Contiene el NavHost con las diferentes rutas y argumentos necesarios para la navegación entre pantallas.
 *
 * @param startDestination Ruta inicial al iniciar la aplicación. Por defecto es la pantalla de login.
 */
@Composable
fun NavGraph(startDestination: String = Screen.Login.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        // Pantalla de Login
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        // Pantalla principal, recibe userId para personalizar contenido
        composable(
            route = "home/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            HomeScreen(navController, userId)
        }

        // Perfil del usuario
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            ProfileScreen(navController, userId)
        }

        // Creación de hábito nuevo
        composable(
            route = Screen.CreateHabit.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            CreateHabitScreen(navController, userId)
        }

        // Registro de usuario
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        // Pantalla de progreso del usuario
        composable(
            route = Screen.Progress.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            ProgressScreen(navController, userId)
        }

        // Actualizar perfil de usuario
        composable(
            route = Screen.UpdateUser.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            UpdateUserScreen(navController, userId)
        }

        // Actualizar hábito
        composable(
            route = Screen.UpdateHabit.route,
            arguments = listOf(navArgument("habitId") { type = NavType.LongType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getLong("habitId") ?: 0L
            UpdateHabitScreen(navController, habitId)
        }

        // Pantalla de recuperar contraseña
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }

        // Selección de hora para notificaciones
        composable(Screen.NotificationsPicker.route) {
            NotificationTimePickerScreen(navController)
        }
    }
}
