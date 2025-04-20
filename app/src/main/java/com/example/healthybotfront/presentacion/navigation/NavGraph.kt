package com.example.healthybotfront.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.healthybotfront.presentacion.ui.screens.login.LoginScreen
import com.example.healthybotfront.presentacion.ui.screens.home.HomeScreen // Asegúrate de tener esta pantalla
import com.example.healthybotfront.presentacion.ui.screens.profile.ProfileScreen
import com.example.healthybotfront.presentation.register.RegisterScreen


@Composable
fun NavGraph(startDestination: String = Screen.Login.route) {
    val navController = rememberNavController()

    // Aquí se define el NavHost y las rutas
    NavHost(navController = navController, startDestination = startDestination) {
        // Ruta para Login
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        // Ruta para Home (Principal)
        composable(
            route = "home/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            HomeScreen(navController, userId)
        }

        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId") ?: 0L
            ProfileScreen(navController, userId)
        }
        // Ruta para Registro
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
    }
}
