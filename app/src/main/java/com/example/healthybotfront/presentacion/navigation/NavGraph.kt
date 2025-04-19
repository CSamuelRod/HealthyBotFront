package com.example.healthybotfront.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthybotfront.presentacion.ui.screens.login.LoginScreen
import com.example.healthybotfront.presentacion.ui.screens.home.HomeScreen // Asegúrate de tener esta pantalla
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
        composable(Screen.Home.route) { backStackEntry ->
            // Extraer el userId de los argumentos pasados desde el login
            val userId = backStackEntry.arguments?.getLong("userId")
            if (userId != null) {
                HomeScreen(navController = navController, userId = userId) // Pasar el userId a HomeScreen
            }
        }

        // Ruta para Registro
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
    }
}
