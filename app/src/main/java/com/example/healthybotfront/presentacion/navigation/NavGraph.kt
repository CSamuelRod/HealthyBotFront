package com.example.healthybotfront.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthybotfront.presentacion.ui.screens.login.LoginScreen
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
        // Ruta para Principal
        /*
        composable(Screen.Principal.route) { backStackEntry ->
            // Extraer el userId de los argumentos
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                MainScreen(navController = navController, userId = userId) // Pasar el userId a MainScreen
            }
        }*/
        composable(Screen.Home.route) {
            RegisterScreen(
                navController
            )
        }

        // Ruta para Registro
        composable(Screen.Register.route) {
            RegisterScreen(
                navController
            )
        }
    }
}