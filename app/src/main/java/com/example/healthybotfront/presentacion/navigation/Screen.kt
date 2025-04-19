package com.example.healthybotfront.presentacion.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")

    object Home : Screen("home/{userId}") {
        fun createRoute(userId: Long) = "home/$userId" // Crea la ruta con el userId
    }

    object Register : Screen("register")
}
